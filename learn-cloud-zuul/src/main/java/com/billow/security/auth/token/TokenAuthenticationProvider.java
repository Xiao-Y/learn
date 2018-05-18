package com.billow.security.auth.token;

import com.billow.context.UserContext;
import com.billow.security.auth.AuthenticationToken;
import com.billow.security.config.TokenProperties;
import com.billow.security.token.impl.RawAccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用 {@link AuthenticationProvider} 的接口提供实现 {@link com.billow.security.token.Token} 身份验证的实例
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
    private final TokenProperties tokenProperties;

    @Autowired
    public TokenAuthenticationProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessToken rawAccessToken = (RawAccessToken) authentication.getCredentials();
        long startTime = System.currentTimeMillis();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(tokenProperties.getSigningKey());
        logger.debug("[验证Token消耗时间] - [{}]", (System.currentTimeMillis() - startTime));
        String subject = jwsClaims.getBody().getSubject();
        @SuppressWarnings("unchecked")
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserContext context = UserContext.create(subject, authorities);
        return new AuthenticationToken(context, context.getAuthorities());
    }

    /**
     * 用于判断是否执行本处理器处理
     *
     * @param authentication 判断标识
     * @return trur 执行，false 不执行
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (AuthenticationToken.class.isAssignableFrom(authentication));
    }
}

