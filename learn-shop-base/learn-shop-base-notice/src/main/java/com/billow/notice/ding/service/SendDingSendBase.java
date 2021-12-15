package com.billow.notice.ding.service;

import com.alibaba.fastjson.JSON;
import com.billow.notice.ding.param.SendRequestParam;
import com.billow.notice.ding.properties.RobotProperties;
import com.billow.notice.ding.util.HmacSha256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SendDingSendBase {

    private SendRequestParam param;

    private RobotProperties robotProperties;

    private RestTemplate restTemplate;

    public SendDingSendBase(RobotProperties robotProperties, RestTemplate restTemplate, SendRequestParam param) {
        this.robotProperties = robotProperties;
        this.restTemplate = restTemplate;
        this.param = param;
    }

    /**
     * 推送钉钉机器人消息
     *
     * @return
     */
    public String send() {
        String dingUrl = this.getDingUrl();
        String json = JSON.toJSONString(param);
        return sendRequest(dingUrl, json);
    }

    protected String sendRequest(String url, String params) {
        log.info("sendRequest()>>>[{}]", params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);
        String body = entity.getBody();
        log.info("sendResponse()>>>[{}]", body);
        return body;
    }

    /**
     * 获取 钉钉机器人地址
     *
     * @param
     * @return String
     * @author 千面
     * @date 2021/11/26 13:36
     */
    protected String getDingUrl() {
        String webhook = robotProperties.getWebhook();
        String robotKey = robotProperties.getRobotKey();
        long timestamp = System.currentTimeMillis();
        String sign = HmacSha256Util.dingHmacSHA256(System.currentTimeMillis(), robotKey);
        // 钉钉机器人地址（配置机器人的 webhook） https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
        return String.format(webhook + "&timestamp=%d&sign=%s", timestamp, sign);
    }
}
