package com.ihason.dtp.easytrans.demos.account.api.feign;

import com.ihason.dtp.easytrans.demos.account.api.dto.AccountDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountService {

    @PostMapping("/v1/accounts/users/{id}")
    AccountDTO deductByUser(@PathVariable("id") Long id, @RequestParam("amount") BigDecimal amount);

}
