package com.billow.notice.amqp.config;

import com.billow.notice.amqp.properties.NoticeMqYml;
import com.billow.notice.amqp.service.SendMQService;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * rabbit mq 配置文件
 *
 * @author liuyongtao
 * @create 2018-06-26 16:02
 */
@Configuration
@EnableConfigurationProperties({NoticeMqYml.class})
public class RabbitMqConfig {
    @Bean
    public SendMQService sendMQService() {
        return new SendMQService();
    }
}
