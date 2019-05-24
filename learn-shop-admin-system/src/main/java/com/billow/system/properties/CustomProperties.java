package com.billow.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 约定的配置信息
 *
 * @author liuyongtao
 * @create 2019-04-30 9:08
 */
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private SecurityProperties security = new SecurityProperties();

    public SecurityProperties getSecurity() {
        return security;
    }

    public CustomProperties setSecurity(SecurityProperties security) {
        this.security = security;
        return this;
    }
}
