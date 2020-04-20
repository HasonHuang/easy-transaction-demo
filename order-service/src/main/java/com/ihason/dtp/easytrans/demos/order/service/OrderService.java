package com.ihason.dtp.easytrans.demos.order.service;

import com.ihason.dtp.easytrans.demos.account.api.dto.EasyTransAccountDTO;
import com.ihason.dtp.easytrans.demos.account.api.feign.AccountService;
import com.ihason.dtp.easytrans.demos.order.dao.OrderDao;
import com.ihason.dtp.easytrans.demos.order.entity.Order;
import com.ihason.dtp.easytrans.demos.order.entity.constant.OrderStatus;
import com.ihason.dtp.easytrans.demos.order.service.saga.request.UpdateOrderStatusRequest;
import com.yiqiniu.easytrans.core.EasyTransFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.ihason.dtp.easytrans.demos.order.service.constant.BizCode.ORDER;
import static org.apache.commons.lang.Validate.notNull;

@Slf4j
@Service
public class OrderService {

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

        // 在本地事务结束后，调用远程服务（这个远程服务实现了SAGA-TCC接口）
        EasyTransAccountDTO.AccountRequestDTO requestDTO = new EasyTransAccountDTO.AccountRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setAmount(order.getAmount());
        transaction.execute(requestDTO);

        // 最后，使用 SAGA-TCC 来异步更新ORDER的状态
        // 框架将会根据全局事务状态来执行 confirm 或者 cancel 方法
        // 这个是 SAGA-TCC 与其他事务类型的明显区别
        // 当 SAGA-TCC 类型事务没有加入到全局事务时，本方法执行的记录都会自动回滚掉
        // 但 SAGA 模式的主控事务会拆分成两个事务，所以，我们需要手工写代码处理清理操作或者确认操作
        UpdateOrderStatusRequest statusRequest = new UpdateOrderStatusRequest();
        statusRequest.setOrderId(order.getId());
        transaction.execute(statusRequest);

        return order;
    }

    /**
     * 确认订单
     */
    public void confirmOrder(Long orderId) {
        Order order = orderDao.selectById(orderId);

        if (order == null) {
            log.warn("Cannot find any order by id: {}", orderId);
            return;
        }

        if (order.getStatus() == OrderStatus.PENDING_CREATE
                || order.getStatus() == OrderStatus.CREATING) {
            order.setStatus(OrderStatus.SUCCESS);
            orderDao.updateById(order);
            return;
        }

        log.warn("Cannot confirm order [{}] status [{}]", order.getId(), order.getStatus());
    }

    /**
     * 取消订单
     */
    public void cancelOrder(Long orderId) {
        Order order = orderDao.selectById(orderId);

        if (order == null) {
            log.warn("Cannot find any order by id: {}", orderId);
            return;
        }

        if (order.getStatus() == OrderStatus.PENDING_CREATE
                || order.getStatus() == OrderStatus.CREATING) {
            order.setStatus(OrderStatus.FAIL);
            orderDao.updateById(order);
            return;
        }

        log.warn("Cannot cancel order [{}] status [{}]", order.getId(), order.getStatus());
    }

}
