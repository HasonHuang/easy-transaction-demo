package com.ihason.dtp.easytrans.demos.account.api.dto.compensable;

import com.ihason.dtp.easytrans.demos.account.api.constant.ServiceConstant;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.cps.CompensableMethodRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 补偿模式的请求参数
 */
@Getter
@Setter
@ToString
@BusinessIdentifer(appId= ServiceConstant.SERVICE_NAME, busCode = ServiceConstant.BUS_CODE_DEDUCT_CPS)
public class CompensableAccountRequestDTO implements Serializable, CompensableMethodRequest<CompensableAccountResponseDTO> {

    private Long userId;
    private BigDecimal amount;

}
