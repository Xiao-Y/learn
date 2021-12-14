package com.billow.notice.amqp;

/**
 * mq 常量
 *
 * @author liuyongtao
 * @since 2021-8-23 8:29
 */
public class MqConstant {

    /**
     * DLX：配置交换机
     */
    public final static String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * DLK：配置路由器
     */
    public final static String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 消息延迟时间
     */
    public final static String X_MESSAGE_TTL = "x-message-ttl";

}
