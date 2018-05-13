package com.ft.security.auth.token.verifier.impl;

import com.ft.security.auth.token.verifier.TokenVerifier;
import org.springframework.stereotype.Component;

/**
 * Token验证过滤器
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
