package com.billow.order.mq.consumer;

import com.billow.order.pojo.vo.SuccessKilledVo;
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
    @RabbitListener(queues = "${config.mq.queue.secKillOrder}")
    public void secKillOrder(SuccessKilledVo data) throws Exception {
        log.info("====>>>data:{}", data);
    }
}
