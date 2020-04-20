package com.ihason.dtp.easytrans.demos.account.api.dto;

import com.ihason.dtp.easytrans.demos.account.api.constant.ServiceConstant;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.saga.SagaTccMethodRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Easy Transaction 通讯 DTO
 *
 * @author Hason
 */
public class EasyTransAccountDTO {

    /**
     * 请求参数
     */
    @Getter @Setter @ToString
    @BusinessIdentifer(appId= ServiceConstant.SERVICE_NAME, busCode = ServiceConstant.BIZ_CODE_DEDUCT)
    public static class AccountRequestDTO implements Serializable, SagaTccMethodRequest {
        private Long userId;
        private BigDecimal amount;
    }

}
