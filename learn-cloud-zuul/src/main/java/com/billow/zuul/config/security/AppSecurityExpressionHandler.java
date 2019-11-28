package com.billow.zuul.config.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * spring boot 2 新添加，与鉴权有关 @authService.hasPermission(request,authentication)
 *
 * @author LiuYongTao
 * @date 2019/11/12 14:49
 */
@Configuration("appSecurityExpressionHandler")
public class AppSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        // 加入上下文，不然 @authService.hasPermission(request,authentication) 异常
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}