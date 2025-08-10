package com.billow.gateway.security.config;

import cn.hutool.core.util.ArrayUtil;
import com.billow.gateway.security.component.RestAuthenticationEntryPoint;
import com.billow.gateway.security.component.RestfulAccessDeniedHandler;
import com.billow.gateway.security.filter.JwtAuthenticationFilter;
import com.billow.gateway.security.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;

import java.util.List;

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final SecurityProperties securityProperties;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtFilter;
    private final ReactiveAuthenticationManager customReactiveAuthenticationManager;
    private final ReactiveAuthorizationManager customReactiveAuthorizationManager;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http.oauth2ResourceServer(oauth2 -> oauth2
//                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                .authenticationEntryPoint(restAuthenticationEntryPoint));

        // 白名单配置
        List<String> whiteList = securityProperties.getWhiteList();
        if (!CollectionUtils.isEmpty(whiteList)) {
            http.authorizeExchange(exchange -> exchange
                    .pathMatchers(ArrayUtil.toArray(whiteList, String.class))
                    .permitAll());
        }

        // 要权限的路径配置
        List<String> needCheck = securityProperties.getNeedCheck();
        http.authorizeExchange(exchange -> exchange
                        .pathMatchers(ArrayUtil.toArray(needCheck, String.class))
                        .access(customReactiveAuthorizationManager)
                        .anyExchange()
                        .authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authenticationManager(customReactiveAuthenticationManager)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(restfulAccessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint))

                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }
}
