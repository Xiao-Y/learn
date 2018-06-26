package com.billow.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2018-06-26 16:02
 */
@Configuration
public class RabbitMqConfig {
    public static final String queueName = "springcloud-server-bus-rabbitmq";

    @Bean
    public Queue rabbitMqQueue() {
        return new Queue(queueName);
    }
}
