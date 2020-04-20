package com.ihason.dtp.easytrans.demos.account.service.saga;

import com.ihason.dtp.easytrans.demos.account.api.dto.EasyTransAccountDTO;
import com.ihason.dtp.easytrans.demos.account.service.AccountService;
import com.yiqiniu.easytrans.protocol.saga.SagaTccMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountSagaTccMethod implements SagaTccMethod<EasyTransAccountDTO.AccountRequestDTO> {

    @Autowired
    private AccountService accountService;

    @Override
    public void sagaTry(EasyTransAccountDTO.AccountRequestDTO param) {
        accountService.tryDeduct(param.getUserId(), param.getAmount());
    }

    @Override
    public void sagaConfirm(EasyTransAccountDTO.AccountRequestDTO param) {
        accountService.confirmDeduct(param.getUserId(), param.getAmount());
    }

    @Override
    public void sagaCancel(EasyTransAccountDTO.AccountRequestDTO param) {
        accountService.cancelDeduct(param.getUserId(), param.getAmount());
    }

    @Override
    public int getIdempotentType() {
        return IDENPOTENT_TYPE_FRAMEWORK;
    }
}
