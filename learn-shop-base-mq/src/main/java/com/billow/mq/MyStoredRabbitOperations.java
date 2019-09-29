package com.billow.mq;

import org.springframework.amqp.core.Message;

import java.util.List;

/**
 * Created by shuai on 2019/5/26.
 */
public interface MyStoredRabbitOperations {

    /**
     * 存储RabbitMQ信息
     *
     * @param correlationId
     * @param exchange
     * @param routingKey
     * @param message
     */
    void saveMessage(String name, String correlationId, String exchange, String routingKey, Message message);

    /**
     * 更新mq消息
     *
     * @param correlationId
     */
    void updateSendMessageSuccess(String name, String correlationId);

    /**
     * 根据correlationId获取mq消息
     * @param correlationId
     * @return
     */
    MessageWithTime getMessageByCorrelationId(String name, String correlationId);

    /**
     * 获取发送失败的mq消息
     *
     * @return
     */
    List<MessageWithTime> getSendFailMessages(String name);

}