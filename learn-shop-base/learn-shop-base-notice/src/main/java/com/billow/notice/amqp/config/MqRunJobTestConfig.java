//package com.billow.notice.amqp.config;
//
//import com.billow.notice.amqp.expand.MqCommon;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 执行sql 配置
// *
// * @author liuyongtao
// * @create 2019-10-31 10:50
// */
//@Configuration
//public class MqRunJobTestConfig implements MqCommon
//{
//
//    @Autowired
//    private BaseMqConfig baseMqConfig;
//
//    @Override
//    public String getQueue() {
//        return baseMqConfig.getQueue().getRunJobTest();
//    }
//
//    @Override
//    public String getExchange() {
//        return baseMqConfig.getExchange().getRunJobTest();
//    }
//
//    @Override
//    public String getRouteKey() {
//        return baseMqConfig.getRouteKey().getRunJobTest();
//    }
//
//    @Bean
//    public Queue runJobTestQueue() {
//        return new Queue(this.getQueue());
//    }
//
//    @Bean
//    public DirectExchange runJobTestExchange() {
//        return new DirectExchange(this.getExchange());
//    }
//
//    @Bean
//    public Binding runJobTestBinding() {
//        return BindingBuilder.bind(this.runJobTestQueue())
//                .to(this.runJobTestExchange())
//                .with(this.getRouteKey());
//    }
//}
