package com.billow.job.consume;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.Message;
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

//    @Autowired
//    private StoredRabbitTemplate storedRabbitTemplate;

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.sendMail}")
    public void sendmailQueue(Message message, Channel channel) throws Exception {
//        Thread.sleep(100);
//        System.out.println("开始消费：" + JSONObject.toJSONString(message));
//        System.out.println("开始消费：" + new String(message.getBody()));
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        try {
//            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("重新放入队列。。。");
//            storedRabbitTemplate.re(message);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.sendMailTrt}")
    public void sendmailQueue(String message) throws Exception {

        System.out.println("时间：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss.SSS"));
        System.out.println("开始消费：" + message);
    }
}
