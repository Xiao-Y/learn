package com.billow.security.auth.token.verifier;

/**
 * Token验证
 */
public interface TokenVerifier {

    /**
     * JWT唯一标识. 能用于防止 JWT重复使用，一次只用一个token
     *
     * @param [jti]
     * @return boolean
     * @author LiuYongTao
     * @date 2018/5/17 8:32
     */
    boolean verify(String jti);
}