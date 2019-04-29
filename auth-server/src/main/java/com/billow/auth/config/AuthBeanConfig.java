package com.billow.auth.config;

import com.billow.auth.properties.OAuth2Properties;
import com.billow.auth.security.config.enhancer.CustomJwtAccessTokenConverter;
import com.billow.auth.service.PermissionService;
import com.billow.auth.service.impl.DefaultPermissionServiceImpl;
import com.billow.auth.service.impl.DefaultUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@Configuration
public class AuthBeanConfig {

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionService permissionService() {
        return new DefaultPermissionServiceImpl();
    }

    @Bean
    @Primary
    public ClientDetailsService clientDetails(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        // token存储,这里使用jwt方式存储
        TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }

    @Bean
    @Primary
    public JwtAccessTokenConverter accessTokenConverter() {
        // Token转换器必须与认证服务一致
        CustomJwtAccessTokenConverter accessTokenConverter = new CustomJwtAccessTokenConverter();
        // 测试用,授权服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());
        return accessTokenConverter;
    }
}
