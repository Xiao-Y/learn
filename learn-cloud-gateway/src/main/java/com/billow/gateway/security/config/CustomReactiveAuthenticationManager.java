package com.billow.gateway.security.config;

import com.billow.gateway.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 自定义认证逻辑
        String token = authentication.getCredentials().toString();
        // 验证token有效性
        if (!jwtTokenUtil.validateToken(token)) {
            return Mono.empty();
        }

        // 从token中获取用户名
        String username = jwtTokenUtil.getUsercodeFromToken(token);

        // 加载用户详细信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Optional.ofNullable(userDetails)
                .map(user -> {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    token,
                                    userDetails.getAuthorities());
                            return Mono.just((Authentication) authenticationToken);
                        }
                )
                .orElse(Mono.empty());
    }
}