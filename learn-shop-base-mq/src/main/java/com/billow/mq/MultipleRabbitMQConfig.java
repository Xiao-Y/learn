package com.billow.mq;

import com.billow.mq.properties.CustomProperties;
import com.billow.mq.properties.MqProperties;
import com.billow.mq.service.StoredOperations;
import com.billow.mq.stored.mysql.service.impl.StoredOperationsByMysql;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by shuai on 2019/4/23.
 */
@Configuration
public class MultipleRabbitMQConfig {

    @Autowired
    private MqProperties mqProperties;

    @Autowired
    private StoredOperations storedOperations;

    @Bean
    @ConditionalOnMissingBean(StoredOperations.class)
    public StoredOperations storedOperationsByMysql() {
        return new StoredOperationsByMysql();
    }

    // mq主连接
    @Bean(name = "publicConnectionFactory")
    @Primary
    public CachingConnectionFactory publicConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(mqProperties.getHost());
        connectionFactory.setPort(mqProperties.getPort());
        connectionFactory.setUsername(mqProperties.getUsername());
        connectionFactory.setPassword(mqProperties.getPassword());
        connectionFactory.setVirtualHost(mqProperties.getVirtualHost());
        connectionFactory.setPublisherConfirms(mqProperties.getPublisherConfirms());
        connectionFactory.setPublisherReturns(mqProperties.getPublisherReturns());
        return connectionFactory;
    }

    @Bean(name = "publicRabbitTemplate")
    @Primary
    public StoredRabbitTemplate publicRabbitTemplate(
            @Qualifier("publicConnectionFactory") ConnectionFactory connectionFactory,
            @Value("${v1.spring.rabbitmq.template.mandatory}") Boolean mandatory) {
        CustomProperties custom = mqProperties.getCustom();
        StoredRabbitTemplate publicRabbitTemplate = new StoredRabbitTemplate(connectionFactory, storedOperations,
                custom.getTemplateName(), custom.getReceiveRetryCount(), custom.getDeliveryMode());
        publicRabbitTemplate.setMandatory(mandatory);
        publicRabbitTemplate.setConfirmCallback(publicRabbitTemplate);
        publicRabbitTemplate.setReturnCallback(publicRabbitTemplate);
        return publicRabbitTemplate;
    }

    // 当 is-retry = true时，开启重试机制
    @ConditionalOnProperty(value = {"v1.spring.rabbitmq.custom.is-retry"}, matchIfMissing = true)
    @Bean
    public RetrySendMessage retrySendMessage(StoredRabbitTemplate publicRabbitTemplate) {
        CustomProperties custom = mqProperties.getCustom();
        return new RetrySendMessage(publicRabbitTemplate, custom.getSendRetryCount(), custom.getCacheThreshold());
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
}