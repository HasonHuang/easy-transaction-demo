package com.ihason.dtp.easytrans.demos.account.controller;

import com.ihason.dtp.easytrans.demos.account.entity.Account;
import com.ihason.dtp.easytrans.demos.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/users/{id}")
    public Account deductByUser(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return accountService.deductByUserId(id, amount);
    }

}
