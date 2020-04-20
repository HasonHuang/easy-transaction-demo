package com.ihason.dtp.easytrans.demos.account.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户实体
 *
 * @author Hason
 */
@Data
@Accessors(chain = true)
public class AccountDTO {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}