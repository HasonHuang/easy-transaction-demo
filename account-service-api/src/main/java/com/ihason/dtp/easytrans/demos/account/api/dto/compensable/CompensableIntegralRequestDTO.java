package com.ihason.dtp.easytrans.demos.account.api.dto.compensable;

import com.ihason.dtp.easytrans.demos.account.api.constant.ServiceConstant;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.cps.CompensableMethodRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 补偿模式的请求参数
 */
@Getter
@Setter
@ToString
@BusinessIdentifer(appId= ServiceConstant.SERVICE_NAME, busCode = ServiceConstant.BUS_CODE_CPS_ADD_INTEGRAL)
public class CompensableIntegralRequestDTO implements Serializable, CompensableMethodRequest<CompensableIntegralResponseDTO> {

    private Long userId;
    private Integer amount;

}
