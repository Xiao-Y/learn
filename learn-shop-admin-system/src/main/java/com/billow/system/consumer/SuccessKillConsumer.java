package com.billow.system.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 秒杀成功订单处理
 *
 * @author liuyongtao
 * @since 2021-8-20 11:02
 */
@Slf4j
@Component
public class SuccessKillConsumer {

    @RabbitHandler
    @RabbitListener(queues = "${notice.mq.mq-collect.sec-kill-to-admin-system.queue}")
    public void secKillOrder(Object data) throws Exception {
        log.info("====>>>data:{}", data);
    }
}
