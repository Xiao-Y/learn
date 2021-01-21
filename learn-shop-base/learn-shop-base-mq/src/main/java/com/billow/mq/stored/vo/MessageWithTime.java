package com.billow.mq.stored.vo;

import org.springframework.amqp.core.Message;

/**
 * 消息模型
 *
 * @author LiuYongTao
 * @date 2019/12/19 9:43
 */
public class MessageWithTime {

    //    // 发送时间
//    private Long time;
    // 交换机名
    private String exchange;
    // routingKey名
    private String routingKey;
    // RabbitMQ发送的message内容
    private Message message;
    // 重试次数
    private Integer tryCount;
    // 消息状态
    private String status;

    public MessageWithTime(Integer tryCount, String exchange, String routingKey, String status, Message message) {
        this.tryCount = tryCount;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.status = status;
        this.message = message;
    }

//    public Long getTime() {
//        return time;
//    }
//
//    public void setTime(Long time) {
//        this.time = time;
//    }

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

    public Integer getTryCount() {
        return tryCount;
    }

    public MessageWithTime setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MessageWithTime setStatus(String status) {
        this.status = status;
        return this;
    }
}