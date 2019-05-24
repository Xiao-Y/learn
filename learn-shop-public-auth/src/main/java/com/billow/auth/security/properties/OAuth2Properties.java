package com.billow.auth.security.properties;

import org.springframework.util.Assert;

/**
 * oauth 配置参数
 *
 * @author liuyongtao
 * @create 2018-12-10 17:42
 */
public class OAuth2Properties {

    /**
     * jwt 签名
     */
    private String jwtSigningKey;
    /**
     * 请求头中携带 token 的名称
     */
    private String tokenHeader = "Authorization";
    /**
     * 过期时长，单位为秒,可以通过配置写入
     */
    private int expiration = 60 * 60 * 24 * 30;

    public String getJwtSigningKey() {
        Assert.notNull(jwtSigningKey, "SigningKey 不能为空，请配置 auth.security.oauth2.jwtSigningKey");
        return jwtSigningKey;
    }

    public OAuth2Properties setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
        return this;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public OAuth2Properties setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }

    public int getExpiration() {
        return expiration;
    }

    public OAuth2Properties setExpiration(int expiration) {
        this.expiration = expiration;
        return this;
    }
}
