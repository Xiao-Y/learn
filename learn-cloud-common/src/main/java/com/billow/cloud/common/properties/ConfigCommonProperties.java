package com.billow.cloud.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liuyongtao
 * @create 2019-08-11 10:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigCommonProperties {

    private MqProperties mq = new MqProperties();
}
