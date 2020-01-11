package com.billow.system.consumer;

import com.alibaba.fastjson.JSONObject;
import com.billow.email.perproties.MailPerproties;
import com.billow.email.service.MailService;
import com.billow.job.pojo.ex.MailEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zuul 发送过来的消息
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class JobToMeConsumer {

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = "${config.mq.queue.sendMail}")
    @RabbitHandler
    public void sendMail(MailEx mailEx) throws Exception {
        log.info(JSONObject.toJSONString(mailEx));
        mailService.sendTemplateMail(mailEx.getToEmails(), mailEx.getSubject(), mailEx.getMailTemplateId(), null);
    }
}
