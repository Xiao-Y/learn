package com.billow.system.consumer;

import com.alibaba.fastjson.JSONObject;
import com.billow.cloud.common.mqvo.MailVo;
import com.billow.system.service.MailService;
import com.billow.system.service.MailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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

    @RabbitListener(queues = "${config.mq.jobToSystem.sendMail}")
    @RabbitHandler
    public void sendMail(MailVo mailVo) throws Exception {
        log.info(JSONObject.toJSONString(mailVo));
        mailService.sendTemplateMail(mailVo.getToEmails(), mailVo.getSubject(), mailVo.getMailCode(), mailVo.getParam());
    }
}
