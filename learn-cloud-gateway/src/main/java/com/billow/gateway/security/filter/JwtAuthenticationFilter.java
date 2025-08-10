package com.billow.gateway.security.filter;

import com.billow.gateway.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 从 header 或其他位置提取认证信息
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Authentication authentication = this.authenticateToken(token);
            // 验证 token 并设置认证信息
            return Optional.ofNullable(authentication)
                    .map(a -> chain.filter(exchange)
                            // 放行并设置认证信息到上下文中
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                    .orElse(chain.filter(exchange));
        }

        return chain.filter(exchange);
    }

    private Authentication authenticateToken(String token) {
        try {
            // 验证token有效性
            if (!jwtTokenUtil.validateToken(token)) {
                return null;
            }
            // 从token中获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);

            // 加载用户详细信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    token,
                    userDetails.getAuthorities()
            );

        } catch (Exception e) {
            log.error("Token解析失败: ", e);
            return null;
        }
    }
}