package com.billow.order.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单生产者
 *
 * @author liuyongtao
 * @create 2018-02-06 16:45
 */
@Component
public class CoreOrderProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;
//    @Autowired
//    private RabbitMqConfig rabbitMqConfig;
//
//    public void sendOrderCar() {
//        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        amqpTemplate.convertAndSend(rabbitMqConfig.getOrderStatusQueue().getName(), message);
//        logger.info("【MQ发送内容】" + message);
//    }
//
//    public void sendOrderCar(OrderVo vo) {
//        vo.setCreatorCode("3333");
//        amqpTemplate.convertAndSend(rabbitMqConfig.getTestQueue().getName(), vo);
//        logger.info("【MQ发送内容】" + vo.toString());
//    }

    //轮训
//    @InboundChannelAdapter(value = Source.OUTPUT)
//    public String timerMessageSource() {
//        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        logger.info("publish message :"+format);
//        return format;
//    }
}
