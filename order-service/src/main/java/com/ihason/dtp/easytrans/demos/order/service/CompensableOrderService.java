package com.ihason.dtp.easytrans.demos.order.service;

import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableAccountRequestDTO;
import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableAccountResponseDTO;
import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableIntegralRequestDTO;
import com.ihason.dtp.easytrans.demos.account.api.feign.AccountService;
import com.ihason.dtp.easytrans.demos.order.dao.OrderDao;
import com.ihason.dtp.easytrans.demos.order.entity.Order;
import com.ihason.dtp.easytrans.demos.order.entity.constant.OrderStatus;
import com.ihason.dtp.easytrans.demos.order.service.compensable.CompensableUpdateOrderStatusRequest;
import com.ihason.dtp.easytrans.demos.order.service.saga.request.UpdateOrderStatusRequest;
import com.yiqiniu.easytrans.core.EasyTransFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import static com.ihason.dtp.easytrans.demos.order.service.constant.BusCode.ORDER;
import static org.apache.commons.lang.Validate.notNull;

/**
 * 使用传统补偿事务的订单服务
 *
 * @author Hason
 */
@Slf4j
@Service
public class CompensableOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private EasyTransFacade transaction;

    @Autowired
    private AccountService accountService;

    /**
     * 创建用户订单
     */
    @Transactional
    public Order create(Long userId, BigDecimal price) {
        notNull(userId, "The argument [userId] cannot be null");
        notNull(price, "The argument [price] cannot be null");

        // 创建本地资源
        Order order = Order.create(userId, price);
        orderDao.insert(order);

        // 声明全局事务ID（创建全局事务）
        transaction.startEasyTrans(ORDER, order.getId());

        // 可补偿服务
        // 在本地事务结束后，调用远程服务（这个远程服务实现了补偿模式）
        // （远程服务使用 @EtCps 标注业务方法）
        CompensableAccountRequestDTO requestDTO = new CompensableAccountRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setAmount(order.getAmount());
        Future<CompensableAccountResponseDTO> accountFuture = transaction.execute(requestDTO);

        // 使用可补偿服务增加用户积分
        CompensableIntegralRequestDTO integralRequest = new CompensableIntegralRequestDTO();
        integralRequest.setUserId(userId);
        integralRequest.setAmount(order.getAmount().intValue());
        transaction.execute(integralRequest);

        // 最后，更新订单状态
        order.setStatus(OrderStatus.SUCCESS);
        orderDao.updateById(order);

        return order;
    }

}
