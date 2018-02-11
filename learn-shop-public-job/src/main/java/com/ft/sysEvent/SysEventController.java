package com.ft.sysEvent;

import com.ft.mq.SysEvent.SysEventInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-02-11 11:38
 */
@RestController
@RequestMapping("/sysEvent")
@EnableBinding(SysEventInterface.class)
public class SysEventController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventInterface sysEventInterface;

    @GetMapping("/sysEventSend")
    public void sysEventSend() {
        String message = "系统事件：测试 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sysEventInterface.outSysEventPublish().send(MessageBuilder.withPayload(message).build());
        logger.info("【MQ发送内容】" + message);
    }
}
