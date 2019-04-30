package com.billow.auth.properties;

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

    public String getJwtSigningKey() {
        Assert.notNull(jwtSigningKey, "SigningKey 不能为空，请配置 auth.security.oauth2.jwtSigningKey");
        return jwtSigningKey;
    }

    public OAuth2Properties setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
        return this;
    }
}
