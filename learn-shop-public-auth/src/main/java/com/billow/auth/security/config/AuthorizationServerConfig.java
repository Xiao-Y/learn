package com.billow.auth.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.billow.auth.security.config.tokenstore.TokenStoreType;
import com.billow.auth.security.endpoint.AuthExceptionEntryPoint;
import com.billow.auth.security.translator.CustomOauthWebResponseExceptionTranslator;
import com.billow.auth.security.translator.TokenOauthWebResponseExceptionTranslator;
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
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;


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
    @Qualifier("redisTokenStoreType")
    private TokenStoreType tokenStoreType;
    @Autowired
    private CustomOauthWebResponseExceptionTranslator customOauthWebResponseExceptionTranslator;
    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                // 若无，refresh_token会有UserDetailsService is required错误
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStoreType.getTokenStore())
                .tokenServices(defaultTokenServices())
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
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     *
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStoreType.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }
}
