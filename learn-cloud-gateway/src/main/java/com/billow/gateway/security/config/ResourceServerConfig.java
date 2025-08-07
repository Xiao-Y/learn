package com.billow.gateway.security.config;

import cn.hutool.core.util.ArrayUtil;
import com.billow.gateway.security.component.RestAuthenticationEntryPoint;
import com.billow.gateway.security.component.RestfulAccessDeniedHandler;
import com.billow.gateway.security.constant.AuthConstant;
import com.billow.gateway.security.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 资源服务器配置
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;
    private final SecurityProperties securityProperties;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    // 暂时不能用
//    private final WhiteListRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        // jwt 增加
//        http.oauth2ResourceServer().jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter());
//        //自定义处理JWT请求头过期或签名错误的结果
//        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
//        //对白名单路径，直接移除JWT请求头
////        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
//        // 白名单
//        List<String> whiteList = securityProperties.getWhiteList();
//        if (!CollectionUtils.isEmpty(whiteList)) {
//            //白名单配置
//            http.authorizeExchange().pathMatchers(ArrayUtil.toArray(whiteList, String.class)).permitAll();
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//            log.info("当前登陆用户的角色：", JSON.toJSONString(authorities));
//            // 如果是 admin 直接放行
//            if (authorities != null && authorities.contains("ADMIN")) {
//                http.authorizeExchange()
//                        .pathMatchers("/**")
//                        .permitAll();
//            }
//        }
//
//        // 要权限的
//        List<String> needCheck = securityProperties.getNeedCheck();
//        http.authorizeExchange()
//                .pathMatchers(ArrayUtil.toArray(needCheck, String.class))
//                .access(authorizationManager)//鉴权管理器配置
//                .and().exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
//                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
//                .and().csrf().disable();
//        return http.build();
        // JWT 配置
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(restAuthenticationEntryPoint));

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
                        .access(authorizationManager)
                        .anyExchange()
                        .authenticated())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(restfulAccessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}