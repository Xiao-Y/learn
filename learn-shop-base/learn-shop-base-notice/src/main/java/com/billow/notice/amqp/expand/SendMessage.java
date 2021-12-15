package com.billow.notice.amqp.expand;

import com.billow.notice.config.SpringContextUtil;
import com.billow.notice.amqp.properties.MqSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * 发送 mq 消息
 *
 * @author liuyongtao
 * @since 2021-8-20 16:00
 */
@Slf4j
public class SendMessage {

    private final static AmqpTemplate amqpTemplate = SpringContextUtil.getBean(AmqpTemplate.class);

    /**
     * 发送消息
     *
     * @param setting mq 配置
     * @param data   消息体
     * @author liuyongtao
     * @since 2021-8-20 16:01
     */
    public static void send(MqSetting setting, Object data) {
        String exchange = setting.getExchange();
        String routeKey = setting.getRouteKey();
        log.info("发送消息：exchange:{},routeKey:{},queue:{},data:{}", exchange, routeKey, setting.getQueue(), data);
        amqpTemplate.convertAndSend(exchange, routeKey, data);
    }

    /**
     * 发送 fanout 消息
     *
     * @param exchange fanout 类型，只需要交换机
     * @param data     消息体
     * @author liuyongtao
     * @since 2021-8-20 16:01
     */
    public static void send(String exchange, Object data) {
        log.info("发送消息：exchange:{},data:{}", exchange, data);
        amqpTemplate.convertAndSend(exchange, null, data);
    }

    /**
     * 发送延时消息
     *
     * @param setting     mq 配置
     * @param data       消息体
     * @param delayTimes 延时时间（分钟）
     * @author liuyongtao
     * @since 2021-8-25 15:21
     */
    public static void send(MqSetting setting, Object data, final long delayTimes) {
        String exchange = setting.getExchange();
        String routeKey = setting.getRouteKey();
        log.info("发送消息：exchange:{},routeKey:{},queue:{},data:{}", exchange, routeKey, setting.getQueue(), data);
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(exchange, routeKey, data, message -> {
            //给消息设置延迟毫秒值
            message.getMessageProperties().setExpiration(String.valueOf(delayTimes * 1000 * 60));
            return message;
        });
    }
}
