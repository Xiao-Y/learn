package com.billow.job.consume;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyongtao
 * @create 2019-09-30 11:03
 */
@Component
public class TestMqConsume {

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.sendMail}")
    public void sendmailQueue(Message message, Channel channel) throws Exception {
//        Thread.sleep(100);
        System.out.println("开始消费：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
