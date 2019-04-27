package com.billow.auth.properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * oauth 配置参数
 *
 * @author liuyongtao
 * @create 2018-12-10 17:42
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth.security.oauth2")
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
