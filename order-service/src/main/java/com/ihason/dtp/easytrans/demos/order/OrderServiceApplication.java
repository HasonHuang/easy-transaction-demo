package com.ihason.dtp.easytrans.demos.order;

import com.ihason.dtp.easytrans.demos.account.api.feign.AccountService;
import com.ihason.dtp.easytrans.demos.order.dao.OrderDao;
import com.yiqiniu.easytrans.EnableEasyTransaction;
import com.yiqiniu.easytrans.extensionsuite.impl.database.EnableExtensionSuiteDatabaseImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 订单服务程序入口
 *
 * @author Hason
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackageClasses = AccountService.class)
@MapperScan(basePackageClasses = OrderDao.class)
@EnableEasyTransaction
@EnableExtensionSuiteDatabaseImpl
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
