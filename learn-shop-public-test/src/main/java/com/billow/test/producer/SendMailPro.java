package com.billow.test.producer;

import com.billow.common.amqp.RabbitMqConfig;
import job.pojo.ex.MailEx;
import job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送邮件
 *
 * @author liuyongtao
 * @create 2019-08-11 10:28
 */
@Slf4j
@Component
public class SendMailPro implements JobService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Override
    public void sendMail(MailEx mailVo) {
        String sendMailRoutingKey = rabbitMqConfig.getSendMailQueue().getName();
        log.info("RoutingKey:{}，发送邮件的 mq", sendMailRoutingKey);
        amqpTemplate.convertAndSend(sendMailRoutingKey, mailVo);
    }
}
