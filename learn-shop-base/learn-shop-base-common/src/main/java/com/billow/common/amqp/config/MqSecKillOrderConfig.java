//package com.billow.common.amqp.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.ExchangeBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 秒杀订单 配置
// *
// * @author liuyongtao
// * @since 2021-8-19 17:35
// */
//@Configuration
//public class MqSecKillOrderConfig {
//
//    @Autowired
//    private BaseMqConfig baseMqConfig;
//
//    /**
//     * 秒杀订单交换机
//     *
//     * @return {@link FanoutExchange}
//     * @author liuyongtao
//     * @since 2021-8-23 9:05
//     */
//    @Bean
//    public FanoutExchange secKillOrderExchange() {
//        return ExchangeBuilder.fanoutExchange(baseMqConfig.getExchange().getSecKillOrder()).build();
//    }
//
//    //**********  秒杀订单发送订单系统 ****************
//    @Bean
//    public Queue secKillToCoreOrderQueue() {
//        return QueueBuilder.durable(baseMqConfig.getQueue().getSecKillToCoreOrder())
//                // 指定死信交换机
//                .deadLetterExchange(baseMqConfig.getExchange().getSecKillOrderDlx())
//                .build();
//    }
//
//    @Bean
//    public Binding coreOrderBinding() {
//        return BindingBuilder.bind(this.secKillToCoreOrderQueue())
//                .to(this.secKillOrderExchange());
//    }
//
//    //**********  秒杀订单发送system系统 ****************
//    @Bean
//    public Queue secKillToAdminSystemQueue() {
//        return QueueBuilder.durable(baseMqConfig.getQueue().getSecKillToAdminSystem()).build();
//    }
//
//    @Bean
//    public Binding adminSystemBinding() {
//        return BindingBuilder.bind(this.secKillToAdminSystemQueue())
//                .to(this.secKillOrderExchange());
//    }
//
//
//    /**
//     * 秒杀订单消费失败时，死信交换机
//     *
//     * @return {@link FanoutExchange}
//     * @author liuyongtao
//     * @since 2021-8-23 9:06
//     */
//    @Bean
//    public FanoutExchange secKillOrderDlxExchange() {
//        return ExchangeBuilder.fanoutExchange(baseMqConfig.getExchange().getSecKillOrderDlx()).build();
//    }
//
//    //**********  秒杀订单发送订单系统（死信） ****************
//    @Bean
//    public Queue secKillToCoreOrderDlxQueue() {
//        return QueueBuilder.durable(baseMqConfig.getQueue().getSecKillToCoreOrderDlx()).build();
//    }
//
//    @Bean
//    public Binding coreOrderDlxBinding() {
//        return BindingBuilder.bind(this.secKillToCoreOrderDlxQueue())
//                .to(this.secKillOrderDlxExchange());
//    }
//}
