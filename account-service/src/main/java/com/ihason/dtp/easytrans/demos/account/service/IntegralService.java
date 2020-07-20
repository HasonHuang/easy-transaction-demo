package com.ihason.dtp.easytrans.demos.account.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ihason.dtp.easytrans.demos.account.dao.IntegralDao;
import com.ihason.dtp.easytrans.demos.account.entity.Account;
import com.ihason.dtp.easytrans.demos.account.entity.Integral;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.Validate.notNull;

/**
 * 积分业务层
 *
 * @author Hason
 */
@Slf4j
@Service
public class IntegralService {

    @Autowired
    private IntegralDao integralDao;

    public Integral add(Long userId, Long amount) {
        notNull(amount, "[amount] cannot be null");

        Integral integral = getIntegral(userId);
        notNull(integral, "Cannot find any integral for user id: " + userId);

        integral.setAmount(addLong(integral.getAmount(), amount));
        integralDao.updateById(integral);
        return integral;
    }

    public Integral deduct(Long userId, Long amount) {
        notNull(amount, "[amount] cannot be null");

        Integral integral = getIntegral(userId);
        notNull(integral, "Cannot find any integral for user id: " + userId);

        Validate.isTrue(integral.getAmount() < amount, "Sorry, your freeze integral is running low");

        integral.setAmount(integral.getAmount() - amount);
        integralDao.updateById(integral);
        return integral;
    }

    private Long addLong(Long a, Long b) {
        return a != null ? a + b : b;
    }

    private Integral getIntegral(Long userId) {
        return integralDao.selectOne(Wrappers.<Integral>lambdaQuery().eq(Integral::getUserId, userId));
    }

}
