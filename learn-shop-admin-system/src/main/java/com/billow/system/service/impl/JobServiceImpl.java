package com.billow.system.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.cloud.common.amqp.AmqpYml;
import com.billow.email.service.MailService;
import com.billow.job.pojo.ex.MailEx;
import com.billow.job.service.JobService;
import com.billow.notice.amqp.service.SendMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
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
    private RestTemplate restTemplate;
    @Autowired
    private SendMQService sendMQService;
    @Resource
    private AmqpYml runJobTest;

    @Override
    public void sendMail(MailEx mailEx) throws Exception {
        log.info(JSONObject.toJSONString(mailEx));

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("logId", mailEx.getLogId());
        parameter.put("messageCode", "code-009");
        parameter.put("messageStatus", "satus-8");
        parameter.put("cause", "No");
        parameter.put("mailCode", "messageParamSQL-FreeMarker");

        mailService.sendTemplateMail(mailEx.getToEmails(), mailEx.getSubject(),
                mailEx.getMailTemplateId(), parameter);

//        MailServiceVo serviceVo = MailServiceVo.getInstance("lyongtao123@126.com", mailEx.getSubject(), "测试附件");
////        serviceVo.setHost("smtp.qq.com");
////        serviceVo.setFromEmail("lyongtao1234@qq.com");
////        serviceVo.setUsername("lyongtao1234@qq.com");
////        serviceVo.setPassword("htamatujqlpkbeji");
//        Map<String, String> attachments = serviceVo.getAttachments();
//        File file = new File("D:\\uploadfile\\usericon\\11.jpg");
//        String fileToString = FileUtils.fileToString(file);
//        attachments.put("11.jpg", fileToString);
//        mailService.sendMail(serviceVo);
    }

    @Override
    public void sendMQ(String routingKey, String param) {
        String runJobExchange = runJobTest.getExchange();
        sendMQService.send(runJobExchange, routingKey, param);
    }


    @Override
    public void httpGet(String url) {
//        restTemplate.getForObject(url, String.class);
        HttpUtil.get(url);
    }

    @Override
    public void httpFeign(String url, Map<String, String> body) {
        restTemplate.getForObject(url, String.class, body);
    }
}
