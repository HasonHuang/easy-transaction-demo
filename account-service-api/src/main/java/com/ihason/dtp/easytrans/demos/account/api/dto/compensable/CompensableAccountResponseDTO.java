package com.ihason.dtp.easytrans.demos.account.api.dto.compensable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 可补偿接口的返回数据
 *
 * @author Hason
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensableAccountResponseDTO implements Serializable {

    /** 账户ID */
    private Long id;
}
