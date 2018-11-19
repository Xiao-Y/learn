package com.billow.user.security.config;

import com.billow.user.security.entrypoint.TokenEmptyEntryPoint;
import com.billow.user.security.handler.TokenAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenEmptyEntryPoint tokenEmptyEntryPoint;
    @Autowired
    private TokenAccessDeniedHandler tokenAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                // swagger end
                .anyRequest().authenticated()
                // 异常处理
                .and().exceptionHandling()
                // 不传令牌，令牌错误（失效）等
                .authenticationEntryPoint(tokenEmptyEntryPoint)
                // 令牌不能访问该资源 （403）异常等
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and().httpBasic();
    }
}
