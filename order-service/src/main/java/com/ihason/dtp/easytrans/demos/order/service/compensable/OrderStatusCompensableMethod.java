package com.ihason.dtp.easytrans.demos.order.service.compensable;

import com.ihason.dtp.easytrans.demos.order.service.OrderService;
import com.yiqiniu.easytrans.protocol.BusinessProvider;
import com.yiqiniu.easytrans.protocol.cps.EtCps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于补偿模式的更新订单状态
 *
 * 事务成功：订单成功
 * 事务失败：订单失败
 *
 * @author Hason
 */
@Slf4j
@Component
public class OrderStatusCompensableMethod {

    @Autowired
    private OrderService orderService;

    @EtCps(cancelMethod = "cancelUpdateStatus", idempotentType = BusinessProvider.IDENPOTENT_TYPE_FRAMEWORK)
    public CompensableUpdateOrderStatusResponse updateStatus(CompensableUpdateOrderStatusRequest request) {
        log.debug("Starting submit order: [{}]", request.getOrderId());
        // 确认订单
//        if (request.getOrderId() != null) {
//            throw new RuntimeException("模拟更新状态失败");
//        }
        orderService.confirmOrder(request.getOrderId());
        return new CompensableUpdateOrderStatusResponse();
    }

    // 补偿操作无需返回值
    public void cancelUpdateStatus(CompensableUpdateOrderStatusRequest request) {
        log.debug("Starting cancel order: [{}]", request.getOrderId());
        // 订单失败
        orderService.cancelOrder(request.getOrderId());
    }

}
