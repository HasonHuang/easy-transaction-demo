package com.ihason.dtp.easytrans.demos.account.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ihason.dtp.easytrans.demos.account.dao.AccountDao;
import com.ihason.dtp.easytrans.demos.account.entity.Account;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.apache.commons.lang.Validate.notNull;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Transactional
    public Account deductByUserId(Long userId, BigDecimal amount) {
        notNull(amount, "The argument [amount] cannot be null");
        Account account = getAccount(userId);
        notNull(account, "Cannot find any account for user id: " + userId);

        account.setAmount(account.getAmount().subtract(amount));
        accountDao.updateById(account);
        return account;
    }

    @Transactional
    public Account tryDeduct(Long userId, BigDecimal amount) {
        Account account = getAccount(userId);
        notNull(account, "Cannot find any account for user id: " + userId);

        Validate.isTrue(account.getAmount().compareTo(amount) >= 0, "Sorry, your balance is running low");

        // 冻结金额
        account.setFreezeAmount(account.getFreezeAmount().add(amount));
        account.setAmount(account.getAmount().subtract(amount));
        account.setUpdatedTime(LocalDateTime.now());
        accountDao.updateById(account);

        return account;
    }

    @Transactional
    public Account confirmDeduct(Long userId, BigDecimal amount) {
        Account account = getAccount(userId);
        notNull(account, "Cannot find any account for user id: " + userId);

        Validate.isTrue(account.getFreezeAmount().compareTo(amount) >= 0, "Sorry, your freeze balance is running low");

        // 扣除冻结金额
        account.setFreezeAmount(account.getFreezeAmount().subtract(amount));
        account.setUpdatedTime(LocalDateTime.now());
        accountDao.updateById(account);

        return account;
    }

    @Transactional
    public Account cancelDeduct(Long userId, BigDecimal amount) {
        Account account = getAccount(userId);
        notNull(account, "Cannot find any account for user id: " + userId);

        Validate.isTrue(account.getFreezeAmount().compareTo(amount) >= 0, "Sorry, your freeze balance is running low");

        // 解冻金额
        account.setFreezeAmount(account.getFreezeAmount().subtract(amount));
        account.setAmount(account.getAmount().add(amount));
        account.setUpdatedTime(LocalDateTime.now());
        accountDao.updateById(account);

        return account;
    }

    private Account getAccount(Long userId) {
        return accountDao.selectOne(Wrappers.<Account>lambdaQuery().eq(Account::getUserId, userId));
    }

}
