package com.billow.order.mq.consumer;

import com.billow.order.pojo.vo.SuccessKilledVo;
import com.billow.order.service.SuccessKilledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.secKillToCoreOrderDlx}")
    public void secKillOrderDlx(SuccessKilledVo data, Message message) {
        log.info("DLX-秒杀订单数据:{}", data);
        log.info("DLX-秒杀订单数据:{}", message);
        MessageProperties messageProperties = message.getMessageProperties();
        String consumerQueue = messageProperties.getConsumerQueue();
        String receivedExchange = messageProperties.getReceivedExchange();
        String receivedRoutingKey = messageProperties.getReceivedRoutingKey();
        log.info("consumerQueue:{},receivedExchange:{},receivedRoutingKey:{}",
                consumerQueue, receivedExchange, receivedRoutingKey);
    }
}
