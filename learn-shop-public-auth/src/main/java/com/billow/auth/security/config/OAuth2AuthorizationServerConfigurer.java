package com.billow.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

//@Configuration
//@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private TokenStore jwtTokenStore;
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserDetailsService customUserDetailsServiceImpl;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 指定认证管理器
        endpoints.authenticationManager(authenticationManager)
                // 指定token 的保存方式
//                .tokenStore(jwtTokenStore)
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                // 指定查询用户信息的实现接口
                .userDetailsService(customUserDetailsServiceImpl);
                // token 的生成方式
//                .accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 客户端访问方式配置数据在数据库中
//        clients.withClientDetails(clientDetailsService);
        clients.inMemory()
                .withClient("app")
                .secret("app")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token", "client_credentials")
                .redirectUris("https://www.baidu.com")
                .scopes("app")
                .autoApprove(true)
                .and()
                .withClient("webapp")
                .secret("webapp")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token", "client_credentials")
                .scopes("webapp")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .allowFormAuthenticationForClients();
        oauthServer.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()");
    }

}
