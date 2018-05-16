package com.billow.security.token.impl;

import com.billow.security.token.Token;
import io.jsonwebtoken.Claims;

/**
 * 验证Token
 */
public class AccessToken implements Token {
    private final String rawToken;
    private Claims claims;

    public AccessToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
