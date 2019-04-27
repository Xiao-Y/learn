package com.billow.auth.config;

import com.billow.auth.service.impl.DefaultUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AuthBeanConfig {

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }
}
