package com.billow.mq.stored.service;

import com.billow.mq.stored.vo.MessageWithTime;
import org.springframework.amqp.core.Message;

import java.util.Date;
import java.util.List;

/**
 * 保存发的 mq 消息接口
 *
 * @author LiuYongTao
 * @date 2019/9/30 10:42
 */
public interface StoredOperationsService {

    /**
     * 存储初始化 mq 信息
     *
     * @param rabbitTemplateName RabbitTemplateName
     * @param correlationId      消息唯一键
     * @param exchange           交换机名称
     * @param routingKey         路由名称
     * @param message            消息
     * @param retryDate          重试时间
     * @param tryCount           重试次数
     */
    void saveInitMessage(String rabbitTemplateName, String correlationId, String exchange, String routingKey,
                         Message message, Date retryDate, Integer tryCount);

    /**
     * 更新 mq 消息为成功
     *
     * @param correlationId
     */
    void updateStautsSuccess(String rabbitTemplateName, String correlationId);

    /**
     * 更新 mq 消息为失败
     *
     * @param correlationId
     */
    void updateStautsFail(String rabbitTemplateName, String correlationId);

    /**
     * 更新 mq 重试次数,每次加一。更新下次发送时间
     *
     * @param correlationId
     */
    void updateNextRetry(String rabbitTemplateName, String correlationId, Date nextRetry, Integer tryCount);

    /**
     * 根据 correlationId 获取 mq 消息
     *
     * @param rabbitTemplateName RabbitTemplateName
     * @param correlationId      消息唯一键
     * @return
     */
    MessageWithTime findMessageByCorrelationId(String rabbitTemplateName, String correlationId);

    /**
     * 需要重新投递，投递失败的mq消息
     *
     * @param rabbitTemplateName RabbitTemplateName
     * @return
     */
    List<MessageWithTime> findRetryMessage(String rabbitTemplateName);

}