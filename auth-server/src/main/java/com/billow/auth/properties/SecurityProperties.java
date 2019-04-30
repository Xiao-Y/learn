package com.billow.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * security 的配置信息
 *
 * @author liuyongtao
 * @create 2019-04-30 9:08
 */
@ConfigurationProperties(prefix = "auth.security")
public class SecurityProperties {

    private OAuth2Properties oauth2 = new OAuth2Properties();

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public SecurityProperties setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
        return this;
    }
}
