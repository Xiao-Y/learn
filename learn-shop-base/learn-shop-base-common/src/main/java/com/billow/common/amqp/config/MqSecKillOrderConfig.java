package com.billow.common.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
public class MqSecKillOrderConfig {

    @Autowired
    private BaseMqConfig baseMqConfig;

    @Bean
    public FanoutExchange secKillOrderExchange() {
        return new FanoutExchange(baseMqConfig.getExchange().getSecKillOrder());
    }

    @Bean
    public Queue secKillToCoreOrderQueue() {
        return new Queue(baseMqConfig.getQueue().getSecKillToCoreOrder());
    }

    @Bean
    public Queue secKillToAdminSystemQueue() {
        return new Queue(baseMqConfig.getQueue().getSecKillToAdminSystem());
    }

    @Bean
    public Binding coreOrderBinding() {
        return BindingBuilder.bind(this.secKillToCoreOrderQueue())
                .to(this.secKillOrderExchange());
    }

    @Bean
    public Binding adminSystemBinding() {
        return BindingBuilder.bind(this.secKillToAdminSystemQueue())
                .to(this.secKillOrderExchange());
    }
}
