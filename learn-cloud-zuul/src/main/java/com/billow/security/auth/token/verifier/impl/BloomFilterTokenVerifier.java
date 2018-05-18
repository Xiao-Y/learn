package com.billow.security.auth.token.verifier.impl;

import com.billow.security.auth.token.verifier.TokenVerifier;
import org.springframework.stereotype.Component;

/**
 * 验证jti是否有交
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {

    @Override
    public boolean verify(String jti) {
        return true;
    }
}
