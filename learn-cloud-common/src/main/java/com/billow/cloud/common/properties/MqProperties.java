package com.billow.cloud.common.properties;

import lombok.Data;

/**
 * mq 相关
 *
 * @author liuyongtao
 * @create 2019-08-11 11:03
 */
@Data
public class MqProperties {

    private String type;
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

    private QueueProperties queue = new QueueProperties();
    private RouteKeyProperties routeKey = new RouteKeyProperties();
    private ExchangeProperties exchange = new ExchangeProperties();
}
