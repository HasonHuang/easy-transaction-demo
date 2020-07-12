package com.ihason.dtp.easytrans.demos.account.service.compensable;

import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableAccountRequestDTO;
import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableAccountResponseDTO;
import com.ihason.dtp.easytrans.demos.account.entity.Account;
import com.ihason.dtp.easytrans.demos.account.service.AccountService;
import com.yiqiniu.easytrans.protocol.BusinessProvider;
import com.yiqiniu.easytrans.protocol.cps.EtCps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现补偿事务的业务
 *
 * @author Hason
 */
@Slf4j
@Component
public class AccountCompensableMethod  {

    @Autowired
    private AccountService service;

    /**
     * 扣减资金，使用框架帮助实现幂等
     */
    @EtCps(cancelMethod = "cancelDeduct", idempotentType = BusinessProvider.IDENPOTENT_TYPE_FRAMEWORK)
    public CompensableAccountResponseDTO deduct(CompensableAccountRequestDTO request) {
        log.debug("Starting deduct [{}] for user [{}]", request.getAmount(), request.getUserId());
        // 直接扣减资金
        Account account = service.deduct(request.getUserId(), request.getAmount());
        return new CompensableAccountResponseDTO(account.getId());
    }

    public CompensableAccountResponseDTO cancelDeduct(CompensableAccountRequestDTO request) {
        log.debug("Starting cancel deduct [{}] for user [{}]", request.getAmount(), request.getUserId());
        // 恢复被扣减的资金
        Account account = service.add(request.getUserId(), request.getAmount());
        return new CompensableAccountResponseDTO(account.getId());
    }

}
