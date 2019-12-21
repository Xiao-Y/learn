package com.billow.mq.retry;

import com.alibaba.fastjson.JSON;
import com.billow.mq.StoredRabbitTemplate;
import com.billow.mq.stored.service.StoredOperationsService;
import com.billow.mq.stored.vo.MessageWithTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 消息发送失败时，重试发送
 *
 * @author liuyongtao
 * @create 2019-09-30 15:32
 */
public class RetrySendMessage {

    private final static Logger LOGGER = LoggerFactory.getLogger(RetrySendMessage.class);

    /**
     * StoredRabbitTemplate的操作，插入，更新，数据
     */
    private StoredOperationsService storedOperationsService;
    /**
     * StoredRabbitTemplate的操作,发送消息
     */
    private StoredRabbitTemplate storedRabbitTemplate;
    // 消息模板名称
    private String rabbitTemplateName;
    // 重试扫描规则
    private Integer retryCount;
    // 补偿消息阈值，超过会打印错误日志
    private Integer cacheThreshold;
    private NextRetryDate nextRetryDate;

    public RetrySendMessage(StoredRabbitTemplate storedRabbitTemplate, Integer retryCount, Integer cacheThreshold, NextRetryDate nextRetryDate) {
        this.storedRabbitTemplate = storedRabbitTemplate;
        this.storedOperationsService = storedRabbitTemplate.getStoredOperationsService();
        this.rabbitTemplateName = storedRabbitTemplate.getRabbitTemplateName();
        this.retryCount = retryCount;
        this.cacheThreshold = cacheThreshold;
        this.nextRetryDate = nextRetryDate;
    }

    /**
     * rabbitmq发送补偿
     */
    @Scheduled(cron = "${v1.spring.rabbitmq.custom.retry-cron}")
    public void startRetry() {
        try {
            // 需要重新投递，投递失败的mq消息
            List<MessageWithTime> list = storedOperationsService.findRetryMessage(rabbitTemplateName);
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            int count = 0;
            for (MessageWithTime messageWithTime : list) {
                String correlationId = messageWithTime.getMessage().getMessageProperties().getCorrelationId();
                if (correlationId == null || "".equals(correlationId)) {
                    LOGGER.error("{} RabbitMQ 补偿发送，发现mq消息格式不正确,correlationId为空,数据为[{}]", rabbitTemplateName, JSON.toJSONString(messageWithTime));
                    continue;
                }
                Integer tryCount = messageWithTime.getTryCount();
                if (tryCount < retryCount) {
                    ++count;
                    // 之前的数据进行重试
                    storedRabbitTemplate.send(messageWithTime.getExchange(), messageWithTime.getRoutingKey(),
                            messageWithTime.getMessage(), new CorrelationData(correlationId));
                    ++tryCount;
                    // 获取下次重试时间
                    Date retryDate = nextRetryDate.nextRetryDate(tryCount);
                    storedOperationsService.updateNextRetry(rabbitTemplateName, correlationId, retryDate, tryCount);
                    LOGGER.info("{} RabbitMQ 补偿发送, messageWithTime:[{}]", rabbitTemplateName, JSON.toJSONString(messageWithTime));
                } else {// 超过最大重试次数，更新消息为失败，人工介入
                    storedOperationsService.updateStautsFail(rabbitTemplateName, correlationId);
                }
            }
            // 打印警告
            if (count > cacheThreshold) {
                LOGGER.error("{} RabbitMQ 补偿发送，发现积压消息过多,此次共重试了[{}]条mq消息", rabbitTemplateName, count);
            }
        } catch (Throwable e) {
            LOGGER.error("{} RabbitMQ 补偿发送 线程暂停出现异常：", rabbitTemplateName, e);
        }
    }
}
