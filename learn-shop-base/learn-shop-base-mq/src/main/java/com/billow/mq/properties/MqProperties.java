package com.billow.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * mq配置文件
 *
 * @author liuyongtao
 * @create 2019-09-30 15:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "v1.spring.rabbitmq")
@PropertySource("classpath:rabbitmqcofnig.properties")
public class MqProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;
    private Boolean publisherConfirms = true;
    private Boolean publisherReturns = true;

    private CustomProperties custom = new CustomProperties();
}
