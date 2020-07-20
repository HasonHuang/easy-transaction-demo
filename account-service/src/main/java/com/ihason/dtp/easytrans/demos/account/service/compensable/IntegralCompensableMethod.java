package com.ihason.dtp.easytrans.demos.account.service.compensable;

import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableIntegralRequestDTO;
import com.ihason.dtp.easytrans.demos.account.api.dto.compensable.CompensableIntegralResponseDTO;
import com.ihason.dtp.easytrans.demos.account.entity.Integral;
import com.ihason.dtp.easytrans.demos.account.service.IntegralService;
import com.yiqiniu.easytrans.protocol.BusinessProvider;
import com.yiqiniu.easytrans.protocol.cps.EtCps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 为积分实现可补偿的业务
 *
 * @author Hason
 */
@Slf4j
@Component
public class IntegralCompensableMethod {

    @Autowired
    private IntegralService service;

    @EtCps(cancelMethod = "deduct", idempotentType = BusinessProvider.IDENPOTENT_TYPE_FRAMEWORK)
    public CompensableIntegralResponseDTO add(CompensableIntegralRequestDTO request) {
        log.info("Starting deduct [{}] for user [{}]", request.getAmount(), request.getUserId());
        // 增加积分
        Integral integral = service.add(request.getUserId(), Long.valueOf(request.getAmount()));

        return new CompensableIntegralResponseDTO(integral.getAmount());
    }

    public void deduct(CompensableIntegralRequestDTO request) {
        log.info("Starting deduct [{}] for user [{}]", request.getAmount(), request.getUserId());
        // 恢复积分
        service.deduct(request.getUserId(), Long.valueOf(request.getAmount()));
    }

}
