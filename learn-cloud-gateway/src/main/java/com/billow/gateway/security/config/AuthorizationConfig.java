package com.billow.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.ReactiveAuthorizationManager;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Configuration
public class AuthorizationConfig {

    @Bean
    public ReactiveAuthorizationManager authorizationManager() {
        return new CustomReactiveAuthorizationManager();
    }
}