package com.billow.auth.config;

import com.billow.auth.service.impl.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author liuyongtao
 * @create 2019-05-21 10:11
 */
@Configuration
public class AppBeanConfig {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
