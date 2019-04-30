package com.billow.auth.security.config;

import com.billow.auth.security.endpoint.AuthExceptionEntryPoint;
import com.billow.auth.security.endpoint.TokenEmptyEntryPoint;
import com.billow.auth.security.handler.TokenAccessDeniedHandler;
import com.billow.auth.security.translator.TokenOauthWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenEmptyEntryPoint tokenEmptyEntryPoint;
    @Autowired
    private TokenAccessDeniedHandler tokenAccessDeniedHandler;
    @Autowired
    private TokenOauthWebResponseExceptionTranslator tokenOauthWebResponseExceptionTranslator;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessRedirectHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(antMatchersPermitAll()).permitAll()
                // 异常处理
                .and().exceptionHandling()
                // 不传令牌，令牌错误（失效）等
                .authenticationEntryPoint(tokenEmptyEntryPoint)
                // 令牌不能访问该资源 （403）异常等
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionTranslator(tokenOauthWebResponseExceptionTranslator);
        resources
                .tokenStore(tokenStore)
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     * 不需要认证的
     *
     * @return java.lang.String[]
     * @author LiuYongTao
     * @date 2019/4/28 10:22
     */
    private String[] antMatchersPermitAll() {
        List<String> all = new ArrayList<>();
        // swagger start
        all.add("/swagger-ui.html");
        all.add("/swagger-resources/**");
        all.add("/images/**");
        all.add("/webjars/**");
        all.add("/v2/api-docs");
        all.add("/configuration/ui");
        all.add("/configuration/security");
        // swagger end
        String[] array = all.toArray(new String[all.size()]);
        return array;
    }
}
