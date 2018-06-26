package com.billow.common.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2018-06-26 16:02
 */
@Configuration
public class RabbitMqConfig {

    @Value("${config.mq.orderToUser.orderStatus}")
    private String orderStatusQueue;
    @Value("${config.mq.tx.SysEventPublish}")
    private String sysEventPublishQueue;
    @Value("${config.mq.tx.SysEventProcess}")
    private String sysEventProcessQueue;

    @Bean
    public Queue orderStatusQueue() {
        return new Queue(orderStatusQueue);
    }

    @Bean
    public Queue sysEventPublishQueue() {
        return new Queue(sysEventPublishQueue);
    }

    @Bean
    public Queue sysEventProcessQueue() {
        return new Queue(sysEventProcessQueue);
    }

    public String getOrderStatusQueue() {
        return orderStatusQueue;
    }

    public String getSysEventPublishQueue() {
        return sysEventPublishQueue;
    }

    public String getSysEventProcessQueue() {
        return sysEventProcessQueue;
    }
}
