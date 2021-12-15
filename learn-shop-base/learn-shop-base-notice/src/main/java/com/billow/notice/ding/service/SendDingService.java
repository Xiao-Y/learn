package com.billow.notice.ding.service;

import com.billow.notice.ding.param.SendRequestParam;
import com.billow.notice.ding.properties.RobotProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SendDingService {
    @Autowired
    private RobotProperties robotProperties;

    @Autowired
    private RestTemplate restTemplate;

    private final static String MSG_TYPE_TEXT = "text";
    private final static String MSG_TYPE_LINK = "link";
    private final static String MSG_TYPE_MARKDOWN = "markdown";
    private final static String MSG_ACTION_CARD = "actionCard";
    private final static String MSG_FEED_CARD = "feedCard";

    /**
     * 发送文本消息
     *
     * @param text
     * @return OapiRobotSendRequest
     * @author 千面
     * @date 2021/11/25 21:21
     */
    public SendDingSend sendText(SendRequestParam.Text text) {
        SendRequestParam request = new SendRequestParam();
        request.setText(text);
        request.setMsgtype(MSG_TYPE_TEXT);
        return new SendDingSend(robotProperties, restTemplate, request);
    }

    /**
     * 发送带连接的消息
     *
     * @param link
     * @return OapiRobotSendRequest
     * @author 千面
     * @date 2021/11/25 21:21
     */
    public SendDingSend sendLink(SendRequestParam.Link link) {
        SendRequestParam request = new SendRequestParam();
        request.setMsgtype(MSG_TYPE_LINK);
        request.setLink(link);
        return new SendDingSend(robotProperties, restTemplate, request);
    }

    /**
     * 发送markdown消息
     *
     * @param markdown
     * @return OapiRobotSendRequest
     * @author 千面
     * @date 2021/11/25 21:21
     */
    public SendDingSend sendMarkdown(SendRequestParam.Markdown markdown) {
        SendRequestParam request = new SendRequestParam();
        request.setMsgtype(MSG_TYPE_MARKDOWN);
        request.setMarkdown(markdown);
        return new SendDingSend(robotProperties, restTemplate, request);
    }

    public SendDingSendBase sendActionCard(SendRequestParam.Actioncard actioncard) {
        SendRequestParam request = new SendRequestParam();
        request.setMsgtype(MSG_ACTION_CARD);
        request.setActionCard(actioncard);
        return new SendDingSendBase(robotProperties, restTemplate, request);
    }


    public SendDingSendBase sendFeedcard(SendRequestParam.Feedcard feedcard) {
        SendRequestParam request = new SendRequestParam();
        request.setMsgtype(MSG_FEED_CARD);
        request.setFeedCard(feedcard);
        return new SendDingSendBase(robotProperties, restTemplate, request);
    }
}
