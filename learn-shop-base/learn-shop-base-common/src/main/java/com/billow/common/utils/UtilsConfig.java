package com.billow.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    public UserTools userTools(){
        return new UserTools();
    }
}
