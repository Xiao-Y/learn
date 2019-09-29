package com.billow.common.amqp;

public interface MqCommon {

    /**
     * 获取队列名
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/9/29 16:03
     */
    String getQueue();

    /**
     * 获取交换机名
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/9/29 16:03
     */
    String getExchange();

    /**
     * 获取路由key
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/9/29 16:03
     */
    String getRouteKey();
}
