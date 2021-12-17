//package com.billow.common.amqp.config;
//
//import com.billow.common.amqp.expand.MqCommon;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 刷新 es 缓存 配置
// *
// * @author liuyongtao
// * @create 2019-10-31 10:50
// */
//@Configuration
//public class MqRefreshEsConfig implements MqCommon {
//
//    @Autowired
//    private BaseMqConfig baseMqConfig;
//
//    @Override
//    public String getQueue() {
//        return baseMqConfig.getQueue().getRefreshEs();
//    }
//
//    @Override
//    public String getExchange() {
//        return baseMqConfig.getExchange().getProduct();
//    }
//
//    @Override
//    public String getRouteKey() {
//        return baseMqConfig.getRouteKey().getRefreshEs();
//    }
//
//    @Bean
//    public Queue refreshEsQueue() {
//        return new Queue(this.getQueue());
//    }
//
//    @Bean
//    public DirectExchange refreshEsExchange() {
//        return new DirectExchange(this.getExchange());
//    }
//
//    @Bean
//    public Binding refreshEsBinding() {
//        return BindingBuilder.bind(this.refreshEsQueue())
//                .to(this.refreshEsExchange())
//                .with(this.getRouteKey());
//    }
//}
