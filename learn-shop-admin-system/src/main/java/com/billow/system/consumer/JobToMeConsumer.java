package com.billow.system.consumer;

import com.alibaba.fastjson.JSONObject;
import com.billow.cloud.common.mqvo.MailVo;
import com.billow.system.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @RabbitListener(queues = "${config.mq.jobToSystem.sendMail}")
    @RabbitHandler
    public void sendMail(MailVo mailVo) throws Exception {
        log.info(JSONObject.toJSONString(mailVo));
        executorService.execute(() -> {
            log.info("开始发送邮件...");
            try {
                mailService.sendSimpleMail(mailVo.getToEmails(), mailVo.getSubject(), mailVo.getContent());
            } catch (Exception e) {
                log.error("邮件发送失败：{}", e.getMessage());
            }
            log.info("完成发送邮件...");
        });
    }
}
