package com.billow.zuul.producer;

import com.billow.cloud.common.properties.ConfigCommonProperties;
import com.billow.zuul.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送mq
 *
 * @author liuyongtao
 * @create 2019-08-11 10:28
 */
@Slf4j
@Component
public class DataRecoveryPro {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    /**
     * 执行sql 和 加载缓存
     *
     * @return void
     * @author billow
     * @date 2019/8/11 10:33
     */
    public void dataRecovery() {
        String executesqlRoutingKey = rabbitMqConfig.getExecutesqlQueue().getName();
        log.info("RoutingKey:{}，发送初始化 sql 的 mq", executesqlRoutingKey);
        amqpTemplate.convertAndSend(executesqlRoutingKey, "开始执行sql...");
    }
}
