package com.billow.mq;

import org.springframework.amqp.core.Message;

/**
 * Created by shuai on 2019/5/10.
 */
public class MessageWithTime {

    // 发送时间
    private Long time;
    // 交换机名
    private String exchange;
    // routingKey名
    private String routingKey;
    // RabbitMQ发送的message内容
    private Message message;

    public MessageWithTime(Long time, String exchange, String routingKey, Message message) {
        this.time = time;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}