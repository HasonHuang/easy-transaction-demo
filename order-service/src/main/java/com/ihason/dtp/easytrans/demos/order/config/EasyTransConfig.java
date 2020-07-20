package com.ihason.dtp.easytrans.demos.order.config;

import com.ihason.dtp.easytrans.demos.core.framework.DatabaseStringCodecImpl;
import com.yiqiniu.easytrans.extensionsuite.impl.database.DatabaseExtensionSuiteProperties;
import com.yiqiniu.easytrans.extensionsuite.impl.database.GetExtentionSuiteDatabase;
import com.yiqiniu.easytrans.stringcodec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyTransConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public StringCodec stringCodec(DatabaseExtensionSuiteProperties properties, GetExtentionSuiteDatabase dataSourceGetter){
        return new DatabaseStringCodecImpl(properties.getTablePrefix(), dataSourceGetter.getDataSource(), dataSourceGetter.getPlatformTransactionManager());
    }


}
