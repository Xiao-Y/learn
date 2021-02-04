package com.billow.cloud.common;

import com.billow.cloud.common.properties.ConfigCommonProperties;
//import com.billow.cloud.common.properties.DruidDSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @since 2021-2-3 16:15
 */
@Configuration
public class CommonConfig {

    @Bean
    public ConfigCommonProperties configCommonProperties(){
        return new ConfigCommonProperties();
    }
//
//    @Bean
//    public DruidDSProperties druidDSProperties(){
//        return new DruidDSProperties();
//    }
}
