package com.billow.system.consumer;

import com.alibaba.fastjson.JSONObject;
import com.billow.common.amqp.vo.MailVo;
import com.billow.email.service.MailService;
import com.billow.system.properties.CustomProperties;
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
    @Autowired
    private CustomProperties customProperties;

    @RabbitListener(queues = "${config.mq.jobToSystem.sendMail}")
    @RabbitHandler
    public void sendMail(MailVo mailVo) throws Exception {
        log.info(JSONObject.toJSONString(mailVo));
        mailService.sendTemplateMail(customProperties.getMail().getFrom(), mailVo.getToEmails(), mailVo.getSubject(),
                mailVo.getMailTemplateId(), mailVo.getParam());
    }
}
