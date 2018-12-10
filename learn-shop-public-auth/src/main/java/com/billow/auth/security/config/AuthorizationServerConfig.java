package com.billow.auth.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.billow.auth.properties.OAuth2Properties;
import com.billow.auth.security.config.enhancer.CustomJwtAccessTokenConverter;
import com.billow.auth.security.endpoint.AuthExceptionEntryPoint;
import com.billow.auth.security.translator.CustomOauthWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("domainUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private CustomOauthWebResponseExceptionTranslator customOauthWebResponseExceptionTranslator;
    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;
    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                // 若无，refresh_token会有UserDetailsService is required错误
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                // jwt 使用,其它不使用
                .accessTokenConverter(accessTokenConverter())
                .exceptionTranslator(customOauthWebResponseExceptionTranslator);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(authExceptionEntryPoint);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 客户端访问方式配置数据在数据库中
        clients.withClientDetails(clientDetails());
    }

    @Bean
    @Primary
    //  客户端访问方式配置数据在数据库中
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public JwtAccessTokenConverter accessTokenConverter() {
        CustomJwtAccessTokenConverter accessTokenConverter = new CustomJwtAccessTokenConverter();
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());
        return accessTokenConverter;
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        // token 保存方式,更多保存方式查看 com.billow.auth.security.config.tokenstore
        TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }
}
