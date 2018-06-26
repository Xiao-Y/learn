package com.billow.consumer;

import com.billow.pojo.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * MQ订单消费
 *
 * @author liuyongtao
 * @create 2018-02-06 17:52
 */
@Component
public class AdminUserConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.orderToUser.orderStatus}")
    public void sinkMessage(String message) {
        logger.info("MQ消费String: " + message);
    }

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.ts.test}")
    public void sinkMessage(TestVo vo) {
        logger.info("MQ消费VO: " + vo.toString());
    }
}
