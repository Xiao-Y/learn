package com.billow.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发送邮件MQ配置
 *
 * @author liuyongtao
 * @create 2019-09-29 15:24
 */
@Configuration
public class RabbitMqSendMailConfig implements MqCommon {

    @Autowired
    private BaseMqConfig baseMqConfig;

    @Override
    public String getQueue() {
        return baseMqConfig.getQueue().getSendMail();
    }

    @Override
    public String getExchange() {
        return baseMqConfig.getExchange().getSendMail();
    }

    @Override
    public String getRouteKey() {
        return baseMqConfig.getRouteKey().getSendMail();
    }

    @Bean
    public Queue sendMailQueue() {
        return new Queue(this.getQueue());
    }

    @Bean
    public DirectExchange sendMailExchange() {
        return new DirectExchange(this.getExchange());
    }

    @Bean
    public Binding sendMailBinding() {
        return BindingBuilder.bind(this.sendMailQueue())
                .to(this.sendMailExchange())
                .with(this.getRouteKey());
    }
}
