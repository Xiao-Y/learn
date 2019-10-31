package com.billow.zuul.config;

import com.billow.cloud.common.properties.ConfigCommonProperties;
import com.billow.cloud.common.properties.MqProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author liuyongtao
 * @create 2018-06-26 16:02
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    private ConfigCommonProperties configCommonProperties;

    public MqProperties getMq() {
        return configCommonProperties.getMq();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        MqProperties mq = this.getMq();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(mq.getHost(), mq.getPort());
        connectionFactory.setUsername(mq.getUsername());
        connectionFactory.setPassword(mq.getPassword());
        connectionFactory.setVirtualHost(mq.getVirtualHost());
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    //必须是prototype类型
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
}
