package com.billow.notice.amqp.config;

import com.billow.notice.amqp.properties.ConfigCommonProperties;
import com.billow.notice.amqp.properties.ExchangeProperties;
import com.billow.notice.amqp.properties.QueueProperties;
import com.billow.notice.amqp.properties.RouteKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 获取 mq 参数的基类
 *
 * @author liuyongtao
 * @create 2019-09-29 15:53
 */
@Configuration
public class BaseMqConfig {

    @Autowired
    private ConfigCommonProperties configCommonProperties;

    public QueueProperties getQueue() {
        return configCommonProperties.getMq().getQueue();
    }

    public RouteKeyProperties getRouteKey() {
        return configCommonProperties.getMq().getRouteKey();
    }

    public ExchangeProperties getExchange() {
        return configCommonProperties.getMq().getExchange();
    }
}
