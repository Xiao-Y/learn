package com.billow.notice.ding.service;

import com.alibaba.fastjson.JSON;
import com.billow.notice.ding.param.SendRequestParam;
import com.billow.notice.ding.properties.RobotProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SendDingSend extends SendDingSendBase {

    private SendRequestParam param;

    public SendDingSend(RobotProperties robotProperties, SendRequestParam param) {
        super(robotProperties, param);
        this.param = param;
    }

    /**
     * 推送钉钉机器人消息
     *
     * @return
     */
    public String send(SendRequestParam.At at) {
        String dingUrl = this.getDingUrl();
        //组装请求内容
        param.setAt(at);
        String json = JSON.toJSONString(param);
        return sendRequest(dingUrl, json);
    }
}
