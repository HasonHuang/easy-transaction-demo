package com.ihason.dtp.easytrans.demos.order.service.saga.request;

import com.ihason.dtp.easytrans.demos.order.constant.ServiceConstant;
import com.ihason.dtp.easytrans.demos.order.service.constant.BizCode;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.saga.SagaTccMethodRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改订单状态的请求参数
 *
 * @author Hason
 */
@Setter @Getter
@BusinessIdentifer(appId= ServiceConstant.SERVICE_NAME, busCode = BizCode.UPDATE_ORDER_STATUS)
public class UpdateOrderStatusRequest implements SagaTccMethodRequest {

    private static final long serialVersionUID = 1L;

    private Long orderId;

}
