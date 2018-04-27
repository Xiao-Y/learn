package com.ft.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单生产者
 *
 * @author liuyongtao
 * @create 2018-02-06 16:45
 */
@Component
@EnableBinding(Source.class)
public class CoreOrderProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Output(Source.OUTPUT)
    private MessageChannel messageChannel;

    public void sendOrderCar() {
        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        messageChannel.send(MessageBuilder.withPayload(message).build());
        logger.info("【MQ发送内容】" + message);
    }

    //轮训
//    @InboundChannelAdapter(value = Source.OUTPUT)
//    public String timerMessageSource() {
//        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        logger.info("publish message :"+format);
//        return format;
//    }
}
