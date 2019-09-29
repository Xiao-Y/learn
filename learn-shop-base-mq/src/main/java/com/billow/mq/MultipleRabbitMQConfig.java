package com.billow.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by shuai on 2019/4/23.
 */
@Configuration
public class MultipleRabbitMQConfig {

    @Bean
    @Primary
    public MyStoredRabbitOperations myStoredRabbitOperationsByCache() {
        return new MyStoredRabbitOperationsByCache();
    }

    // mq主连接
    @Bean(name = "publicConnectionFactory")
    @Primary
    public CachingConnectionFactory publicConnectionFactory(
            @Value("${v1.spring.rabbitmq.host}") String host,
            @Value("${v1.spring.rabbitmq.port}") int port,
            @Value("${v1.spring.rabbitmq.username}") String username,
            @Value("${v1.spring.rabbitmq.password}") String password,
            @Value("${v1.spring.rabbitmq.virtual-host}") String virtualHost,
            @Value("${v1.spring.rabbitmq.publisher-confirms}") Boolean publisherConfirms,
            @Value("${v1.spring.rabbitmq.publisher-returns}") Boolean publisherReturns) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

    @Bean(name = "publicRabbitTemplate")
    @Primary
    public MyStoredRabbitTemplate publicRabbitTemplate(
            @Qualifier("publicConnectionFactory") ConnectionFactory connectionFactory,
            @Value("${v1.spring.rabbitmq.template.mandatory}") Boolean mandatory) {
        MyStoredRabbitTemplate publicRabbitTemplate = new MyStoredRabbitTemplate(connectionFactory, myStoredRabbitOperationsByCache(), "publicRabbitTemplate");
        publicRabbitTemplate.setMandatory(mandatory);
        publicRabbitTemplate.setConfirmCallback(publicRabbitTemplate);
        publicRabbitTemplate.setReturnCallback(publicRabbitTemplate);
        return publicRabbitTemplate;
    }

    @Bean(name = "publicContainerFactory")
    public SimpleRabbitListenerContainerFactory insMessageListenerContainer(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("publicConnectionFactory") ConnectionFactory connectionFactory,
            @Value("${v1.spring.rabbitmq.listener.simple.acknowledge-mode}") String acknowledge,
            @Value("${v1.spring.rabbitmq.listener.simple.prefetch}") Integer prefetch) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.valueOf(acknowledge.toUpperCase()));
        factory.setPrefetchCount(prefetch);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "publicRabbitAdmin")
    public RabbitAdmin publicRabbitAdmin(
            @Qualifier("publicConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }
}