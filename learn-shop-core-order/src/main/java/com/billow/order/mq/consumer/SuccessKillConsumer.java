package com.billow.order.mq.consumer;

import com.billow.order.pojo.vo.OrderMqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
    @RabbitListener(queues = {"${config.mq.queue.secKillToCoreOrder}"})
    public void secKillOrder(OrderMqVo data) {
        log.info("订单数据:{}", data);
        // TODO 查询商品数据，生成订单数据
        // TODO 订单数据生成完成后，添加订单失效数据（延迟队列）。
    }

    @RabbitHandler
    @RabbitListener(queues = {"${config.mq.queue.secKillToCoreOrderDlx}"})
    public void secKillOrderDlx(OrderMqVo data, Message message) {
        log.info("DLX-订单数据:{}", data);
        log.info("DLX-订单数据:{}", message);
        MessageProperties messageProperties = message.getMessageProperties();
        String consumerQueue = messageProperties.getConsumerQueue();
        String receivedExchange = messageProperties.getReceivedExchange();
        String receivedRoutingKey = messageProperties.getReceivedRoutingKey();
        log.info("consumerQueue:{},receivedExchange:{},receivedRoutingKey:{}",
                consumerQueue, receivedExchange, receivedRoutingKey);
    }
}
