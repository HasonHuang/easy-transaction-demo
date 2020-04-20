package com.ihason.dtp.easytrans.demos.order.service.saga;

import com.ihason.dtp.easytrans.demos.order.service.OrderService;
import com.ihason.dtp.easytrans.demos.order.service.saga.request.UpdateOrderStatusRequest;
import com.yiqiniu.easytrans.protocol.saga.SagaTccMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusSagaTccMethod implements SagaTccMethod<UpdateOrderStatusRequest> {

    @Autowired
    private OrderService orderService;

    @Override
    public int getIdempotentType() {
        return IDENPOTENT_TYPE_FRAMEWORK;
    }

    @Override
    public void sagaTry(UpdateOrderStatusRequest param) {
        //DO NOTHING
    }

    @Override
    public void sagaConfirm(UpdateOrderStatusRequest param) {
        orderService.confirmOrder(param.getOrderId());
    }

    @Override
    public void sagaCancel(UpdateOrderStatusRequest param) {
        orderService.cancelOrder(param.getOrderId());
    }

}
