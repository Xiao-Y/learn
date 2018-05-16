package com.billow.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * MQ订单消费
 *
 * @author liuyongtao
 * @create 2018-02-06 17:52
 */
@EnableBinding(Sink.class)
public class AdminUserConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener(Sink.INPUT)
    public void sinkMessage(Object message) {
        logger.info("MQ消费: " + message);
    }
}
