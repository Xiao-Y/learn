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
    public String orderStatus;

    @Bean
    public Queue orderStatusQueue() {
        return new Queue(orderStatus);
    }
}
