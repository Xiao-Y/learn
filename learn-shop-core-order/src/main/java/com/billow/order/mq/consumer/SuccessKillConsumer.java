package com.billow.order.mq.consumer;

import com.billow.order.pojo.vo.SuccessKilledVo;
import com.billow.order.service.SuccessKilledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SuccessKilledService successKilledService;

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.secKillToCoreOrder}")
    public void secKillOrder(SuccessKilledVo data) {
        log.info("秒杀订单数据:{}", data);
        successKilledService.saveAsync(data.getSeckillId(), data.getUsercode());
    }
}
