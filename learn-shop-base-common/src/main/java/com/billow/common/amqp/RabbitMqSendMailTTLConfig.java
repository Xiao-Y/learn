package com.billow.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件MQ配置
 *
 * @author liuyongtao
 * @create 2019-09-29 15:24
 */
@Configuration
public class RabbitMqSendMailTTLConfig {

    @Autowired
    private BaseMqConfig baseMqConfig;

    public String getExchange() {
        return baseMqConfig.getExchange().getSendMail();
    }

    // 转发队列
    public String getQueueTrt() {
        return baseMqConfig.getQueue().getSendMailTrt();
    }

    // 转发路由
    public String getRouteKeyTrt() {
        return baseMqConfig.getRouteKey().getSendMailTrt();
    }

//    public String getExchangeTrt() {
//        return baseMqConfig.getExchange().getSendMailTrt();
//    }

    // 延迟队列
    public String getQueueDlx() {
        return baseMqConfig.getQueue().getSendMailDlx();
    }

    // 延迟路由
    public String getRouteKeyDlx() {
        return baseMqConfig.getRouteKey().getSendMailDlx();
    }
//
//    public String getExchangeDlx() {
//        return baseMqConfig.getExchange().getSendMailDlx();
//    }

    // 交换机
    @Bean
    public DirectExchange sendMailExchangeTTL() {
        return new DirectExchange(this.getExchange(), true, false);
    }

    // 转发队列
    @Bean
    public Queue sendMailQueueTrt() {
        return new Queue(this.getQueueTrt(), true);
    }

    // 转发队列与转发交换机绑定
    @Bean
    public Binding sendBindingTrt() {
        return BindingBuilder.bind(this.sendMailQueueTrt())
                .to(this.sendMailExchangeTTL())
                .with(this.getRouteKeyTrt());
    }

//    // 交换机
//    @Bean
//    public DirectExchange sendMailExchangeDlx() {
//        return new DirectExchange(this.getExchangeDlx(), true, false);
//    }

    // ttl 队列
    @Bean
    public Queue sendMailQueueDlx() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", this.getExchange());
        args.put("x-dead-letter-routing-key", this.getRouteKeyTrt());
        return new Queue(this.getQueueDlx(), true, false, false, args);
    }

    // ttl 队列与 死信交换机 绑定
    @Bean
    public Binding sendBindingDlx() {
        return BindingBuilder.bind(this.sendMailQueueDlx())
                .to(this.sendMailExchangeTTL())
                .with(this.getRouteKeyDlx());
    }
}
