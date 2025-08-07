package com.billow.cloud.common.amqp;

import lombok.Data;

@Data
public class AmqpYml {
    /**
     * 交换机名称
     *
     * @author xiaoy
     * @since 2021/12/14 22:40
     */
    private String exchange;

    /**
     * 交换机类型，默认 direct
     *
     * @author xiaoy
     * @since 2021/12/14 22:47
     */
    private String exchangeType;

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
