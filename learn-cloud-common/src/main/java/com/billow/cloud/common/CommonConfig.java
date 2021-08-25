package com.billow.cloud.common;

import com.billow.cloud.common.properties.ConfigCommonProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @since 2021-2-3 16:15
 */
@Configuration
public class CommonConfig {

    @Bean
    @ConfigurationProperties(prefix = "config")
    public ConfigCommonProperties configCommonProperties() {
        return new ConfigCommonProperties();
    }
}
