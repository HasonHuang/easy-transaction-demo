package com.ihason.dtp.easytrans.demos.order.service.compensable;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新订单状态的返回值
 *
 * 必须要有返回值，否则SpringMVC 报错： IllegalArgumentException: No converter found for return value of type: class
 *
 * @author Hason
 */
@Data
public class CompensableUpdateOrderStatusResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

}
