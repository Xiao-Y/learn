package com.billow.notice.ding.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.billow.notice.ding.param.SendRequestParam;
import com.billow.notice.ding.properties.RobotProperties;
import com.billow.notice.ding.util.HmacSha256Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendDingSendBase {

    private SendRequestParam param;

    private RobotProperties robotProperties;

    public SendDingSendBase(RobotProperties robotProperties, SendRequestParam param) {
        this.robotProperties = robotProperties;
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
        log.info("发送消息入参：>>>{}", params);
        String body = HttpUtil.post(url, params);
        log.info("消息发送完成：<<<{}", body);
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
