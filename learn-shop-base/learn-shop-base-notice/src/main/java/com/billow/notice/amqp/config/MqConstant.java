package com.billow.notice.amqp.config;

/**
 * mq 常量
 *
 * @author liuyongtao
 * @since 2021-8-23 8:29
 */
public class MqConstant
{

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

    /**
     * 队列默认后缀
     *
     * @author 千面
     * @date 2021/12/15 10:36
     */
    public final static String SUFFIX_QUEUE = "Queue";
    /**
     * 交换机默认后缀
     *
     * @author 千面
     * @date 2021/12/15 10:36
     */
    public final static String SUFFIX_EXCHANGE = "Exchange";
    /**
     * 路由 key 默认后缀
     *
     * @author 千面
     * @date 2021/12/15 10:36
     */
    public final static String SUFFIX_ROUTE_KEY = "RouteKey";
    /**
     * 绑定默认后缀
     *
     * @author 千面
     * @date 2021/12/15 10:36
     */
    public final static String SUFFIX_BINDING = "Binding";

}
