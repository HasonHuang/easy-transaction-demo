package com.ihason.dtp.easytrans.demos.account;

import com.ihason.dtp.easytrans.demos.account.dao.AccountDao;
import com.yiqiniu.easytrans.EnableEasyTransaction;
import com.yiqiniu.easytrans.extensionsuite.impl.database.EnableExtensionSuiteDatabaseImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 账户服务程序入口
 *
 * @author Hason
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackageClasses = AccountDao.class)
@EnableEasyTransaction
@EnableExtensionSuiteDatabaseImpl
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
