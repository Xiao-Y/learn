package com.billow.auth.config;

import com.billow.auth.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author liuyongtao
 * @create 2019-05-21 10:11
 */
@Configuration
public class RbacBeanConfig {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }
}
