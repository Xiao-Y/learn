package com.billow.notice.amqp.properties;

import lombok.Data;
import org.springframework.amqp.core.ExchangeTypes;

/**
 * mq 的队列、路由、交换机配置
 *
 * @author xiaoy
 * @since 2021/12/14 22:40
 */
@Data
public class MqSetting
{

    /**
     * 交换机名称
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String exchange;

    /**
     * 交换机类型，默认 direct
     * {@link ExchangeTypes}
     *
     * @author xiaoy
     * @since 2021/12/14 22:47
     */
    private String exchangeType = ExchangeTypes.DIRECT;

    /**
     * 队列
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String queue;

    /**
     * 路由
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String routeKey;

    /**
     * 消息持久化
     *
     * @author xiaoy
     * @since 2021/12/15 22:26
     */
    private Boolean durable = false;

    /**
     * 延迟交换机名称
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String ttlExchange;


    /**
     * 延迟队列
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String ttlQueue;

    /**
     * 延迟路由
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String ttlRouteKey;

    /**
     * 定义消息的过期时间（毫秒）
     * <p>
     * 默认 10s
     *
     * @author 千面
     * @date 2021/12/17 15:54
     */
    private int messageTtl = 10 * 1000;

    /**
     * 死信交换机名称（如果配置，延迟消息会转发到对应的交换机、路由、队列）
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String dlxExchange;


    /**
     * 死信交换机类型，默认 direct
     * {@link ExchangeTypes}
     *
     * @author xiaoy
     * @since 2021/12/14 22:47
     */
    private String dlxExchangeType = ExchangeTypes.DIRECT;

    /**
     * 死信路由（如果配置，延迟消息会转发到对应的交换机、路由、队列）
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String dlxRouteKey;


    /**
     * 死信队列（如果配置，延迟消息会转发到对应的交换机、路由、队列）
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String dlxQueue;

}
