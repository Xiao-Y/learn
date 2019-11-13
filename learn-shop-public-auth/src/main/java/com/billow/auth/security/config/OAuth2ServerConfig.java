package com.billow.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OAuth2ServerConfig {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        @Qualifier("customTokenServices")
        private ResourceServerTokenServices customTokenServices;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId("resource") //resource资源只允许基于令牌的身份验证
                    .stateless(true)
                    .tokenServices(customTokenServices);

        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    // 由于我们希望在用户界面中访问受保护的资源，因此我们需要允许创建会话
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    //启用匿名登录，不可访问受保护资源
                    .anonymous()
                    .and()
                    .authorizeRequests()
                    //配置protected访问控制，必须认证过后才可以访问
                    .antMatchers("/**/*Api/**", "/*Api/**").authenticated();

        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        private TokenStore jwtTokenStore;
        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;
        @Autowired
        @Qualifier("customClientDetailsService")
        private ClientDetailsService customClientDetailsService;
        @Autowired
        @Qualifier("jwtTokenEnhancer")
        private TokenEnhancer jwtTokenEnhancer;
        @Autowired
        @Qualifier("loggingExceptionTranslator")
        private WebResponseExceptionTranslator loggingExceptionTranslator;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 客户端访问方式配置数据在数据库中
            clients.withClientDetails(customClientDetailsService);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);

            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            enhancerChain.setTokenEnhancers(enhancerList);

            endpoints
                    .tokenStore(jwtTokenStore)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .authenticationManager(authenticationManager)
                    .tokenEnhancer(enhancerChain)
                    .exceptionTranslator(loggingExceptionTranslator);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            security.tokenKeyAccess("permitAll()")
                    .checkTokenAccess("authenticated()")
                    .allowFormAuthenticationForClients(); //允许表单认证
        }
    }

}
