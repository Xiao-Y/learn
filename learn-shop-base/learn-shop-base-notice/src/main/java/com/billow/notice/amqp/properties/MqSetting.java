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
}
