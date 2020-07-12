package com.ihason.dtp.easytrans.demos.order.service.compensable;

import com.ihason.dtp.easytrans.demos.order.constant.ServiceConstant;
import com.ihason.dtp.easytrans.demos.order.service.constant.BusCode;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.cps.CompensableMethodRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 更新订单状态的请求参数
 *
 * @author Hason
 */
@Setter
@Getter
@BusinessIdentifer(appId= ServiceConstant.SERVICE_NAME, busCode = BusCode.UPDATE_ORDER_STATUS_CPS)
public class CompensableUpdateOrderStatusRequest implements Serializable, CompensableMethodRequest<CompensableUpdateOrderStatusResponse> {

    private static final long serialVersionUID = 1L;

    private Long orderId;

}
