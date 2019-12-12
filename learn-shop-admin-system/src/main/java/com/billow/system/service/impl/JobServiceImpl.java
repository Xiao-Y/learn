package com.billow.system.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.email.service.MailService;
import com.billow.job.pojo.ex.MailEx;
import com.billow.job.service.JobService;
import com.billow.system.properties.CustomProperties;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Zuul 发送过来的消息
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class JobServiceImpl implements JobService {

    @Autowired
    private MailService mailService;
    @Autowired
    private CustomProperties customProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMail(MailEx mailEx) {
        log.info(JSONObject.toJSONString(mailEx));

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("logId", mailEx.getLogId().toString());

        mailService.sendTemplateMail(customProperties.getMail().getFrom(), mailEx.getToEmails(), mailEx.getSubject(),
                mailEx.getMailTemplateId(), parameter);
    }

    @Override
    public void sendMQ(String routingKey, String param) {
        rabbitTemplate.convertAndSend(routingKey, param);
    }


    @Override
    public void httpGet(String url) {
//        restTemplate.getForObject(url, String.class);
        HttpUtil.get(url);
    }
}
