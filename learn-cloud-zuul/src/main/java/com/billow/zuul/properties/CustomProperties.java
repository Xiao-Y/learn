package com.billow.zuul.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 约定的配置信息
 *
 * @author liuyongtao
 * @create 2019-04-30 9:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private SecurityProperties security = new SecurityProperties();
}
