package com.billow.mq.service;

import com.billow.mq.MessageWithTime;
import org.springframework.amqp.core.Message;

import java.util.List;

/**
 * 保存发的 mq 消息接口
 *
 * @author LiuYongTao
 * @date 2019/9/30 10:42
 */
public interface StoredOperations {

    /**
     * 存储初始化 mq 信息
     *
     * @param name          RabbitTemplateName
     * @param correlationId 消息唯一键
     * @param exchange      交换机名称
     * @param routingKey    路由名称
     * @param message       消息
     */
    void saveInitMessage(String name, String correlationId, String exchange, String routingKey, Message message);

    /**
     * 更新 mq 消息为成功
     *
     * @param correlationId
     */
    void updateSendMessageSuccess(String name, String correlationId);

    /**
     * 更新 mq 消息为失败
     *
     * @param correlationId
     */
    void updateSendMessageFail(String name, String correlationId);

    /**
     * 更新 mq 重试次数,每次加一
     *
     * @param correlationId
     */
    void updateTryCountAddOne(String name, String correlationId);

    /**
     * 根据 correlationId 获取 mq 消息
     *
     * @param name          RabbitTemplateName
     * @param correlationId 消息唯一键
     * @return
     */
    MessageWithTime getMessageByCorrelationId(String name, String correlationId);

//    /**
//     * 获取发送失败的mq消息
//     *
//     * @param name RabbitTemplateName
//     * @return
//     */
//    List<MessageWithTime> getSendFailMessages(String name);

    /**
     * 需要重新投递，投递失败的mq消息
     *
     * @param name RabbitTemplateName
     * @return
     */
    List<MessageWithTime> findRetrySendMessage(String name);

}