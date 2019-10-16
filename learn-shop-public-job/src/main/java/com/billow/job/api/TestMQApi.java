package com.billow.job.api;

import com.billow.common.amqp.RabbitMqSendMailConfig;
import com.billow.mq.StoredRabbitTemplate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-09-30 10:00
 */
@RestController
@RequestMapping("/testMQApi")
public class TestMQApi {

    @Autowired
    private StoredRabbitTemplate publicRabbitTemplate;

    @Autowired
    private RabbitMqSendMailConfig sendMailConfig;

    @GetMapping("/sendMessage")
    public void sendMessage() {
        publicRabbitTemplate.messageSendMQ(sendMailConfig.getExchange(), sendMailConfig.getRouteKey(),
                "发送测试消息：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss.SSS"));
    }

}
