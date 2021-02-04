package com.billow.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqLogCollectConfig implements MqCommon {
    @Autowired
    private BaseMqConfig baseMqConfig;

    @Override
    public String getQueue() {
        return baseMqConfig.getQueue().getLogCollect();
    }

    @Override
    public String getExchange() {
        return baseMqConfig.getExchange().getLogCollect();
    }

    @Override
    public String getRouteKey() {
        return baseMqConfig.getRouteKey().getLogCollect();
    }

    @Bean
    public Queue logCollectQueue() {
        return new Queue(this.getQueue());
    }

    @Bean
    public DirectExchange logCollectExchange() {
        return new DirectExchange(this.getExchange());
    }

    @Bean
    public Binding logCollectBinding() {
        return BindingBuilder.bind(this.logCollectQueue())
                .to(this.logCollectExchange())
                .with(this.getRouteKey());
    }
}
