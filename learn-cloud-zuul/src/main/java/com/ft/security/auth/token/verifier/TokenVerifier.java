package com.ft.security.auth.token.verifier;

/**
 * Token验证
 */
public interface TokenVerifier {
    boolean verify(String jti);
}