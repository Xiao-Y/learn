package com.billow.auth.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security 的配置信息
 *
 * @author liuyongtao
 * @create 2019-04-30 9:08
 */
@ConfigurationProperties(prefix = "auth.security")
public class SecurityProperties {

    private OAuth2Properties oauth2 = new OAuth2Properties();
    private ClientProperties client = new ClientProperties();

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public SecurityProperties setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
        return this;
    }

    public ClientProperties getClient() {
        return client;
    }

    public SecurityProperties setClient(ClientProperties client) {
        this.client = client;
        return this;
    }
}
