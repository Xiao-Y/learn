package com.billow.product.consume;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-09-30 11:03
 */
@Component
public class TestMqConsume {

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.runJobTest}")
    public void runJobTestQueue(String message) throws Exception {
        System.out.println("时间：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss.SSS"));
        System.out.println("开始消费：" + message);
    }
}
