//package com.billow.job.producer;
//
//import com.billow.common.amqp.RabbitMqConfig;
//import com.billow.common.amqp.RabbitMqSendMailConfig;
//import com.billow.job.pojo.ex.MailEx;
//import com.billow.job.service.JobService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 发送邮件
// *
// * @author liuyongtao
// * @create 2019-08-11 10:28
// */
//@Slf4j
//@Component
//public class SendMailPro implements JobService {
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//    @Autowired
//    private RabbitMqConfig rabbitMqConfig;
//
//    @Autowired
//    private RabbitMqSendMailConfig rabbitMqSendMailConfig;
//
//    /**
//     * 邮件
//     *
//     * @return void
//     * @author billow
//     * @date 2019/8/11 10:33
//     */
//    @Override
//    public void sendMail(MailEx mailEx) {
//        String exchange = rabbitMqSendMailConfig.getExchange();
//        String routeKey = rabbitMqSendMailConfig.getRouteKey();
//        log.info("exchange:{},routeKey:{}，发送邮件的 mq", exchange, routeKey);
//        amqpTemplate.convertAndSend(exchange, routeKey, mailEx);
//    }
//
////    /**
////     * 邮件
////     *
////     * @return void
////     * @author billow
////     * @date 2019/8/11 10:33
////     */
////    @Override
////    public void sendMail(MailEx mailEx) {
////        String sendMailRoutingKey = rabbitMqConfig.getSendMailQueue().getName();
////        log.info("RoutingKey:{}，发送邮件的 mq", sendMailRoutingKey);
////        amqpTemplate.convertAndSend(sendMailRoutingKey, mailEx);
////    }
//}
