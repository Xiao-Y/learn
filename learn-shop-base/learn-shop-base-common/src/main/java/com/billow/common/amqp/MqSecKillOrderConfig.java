package com.billow.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 秒杀订单 配置
 *
 * @author liuyongtao
 * @since 2021-8-19 17:35
 */
@Configuration
public class MqSecKillOrderConfig implements MqCommon {

    @Autowired
    private BaseMqConfig baseMqConfig;

    @Override
    public String getQueue() {
        return baseMqConfig.getQueue().getSecKillOrder();
    }

    @Override
    public String getExchange() {
        return baseMqConfig.getExchange().getSecKill();
    }

    @Override
    public String getRouteKey() {
        return baseMqConfig.getRouteKey().getSecKillOrder();
    }

    @Bean
    public Queue secKillOrderQueue() {
        return new Queue(this.getQueue());
    }

    @Bean
    public DirectExchange secKillOrderExchange() {
        return new DirectExchange(this.getExchange());
    }

    @Bean
    public Binding secKillOrderBinding() {
        return BindingBuilder.bind(this.secKillOrderQueue())
                .to(this.secKillOrderExchange())
                .with(this.getRouteKey());
    }
}
