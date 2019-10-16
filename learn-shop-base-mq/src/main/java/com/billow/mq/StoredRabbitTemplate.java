package com.billow.mq;

import com.alibaba.fastjson.JSON;
import com.billow.mq.service.StoredOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.UUID;

/**
 * 发送消息模板
 *
 * @author LiuYongTao
 * @date 2019/10/16 16:17
 */
public class StoredRabbitTemplate extends RabbitTemplate implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(StoredRabbitTemplate.class);
    /**
     * 重试次数key
     */
    private final static String COUNT_TIME = "count_time";

    private final static Integer ONE = 1;

    private int receiveRetryCount;

    /**
     * MyRabbitTemplate的name
     */
    private String rabbitTemplateName;

    /**
     * 设置消息是否持久化。2-Persistent表示持久化，1-Non-persistent表示不持久化
     */
    private int deliveryMode;

    /**
     * MyStoredRabbitTemplate的操作，插入，更新，数据
     */
    private StoredOperations storedOperations;

    /**
     * @param connectionFactory  rabbitmq连接工厂
     * @param storedOperations   重试策略
     * @param rabbitTemplateName 自定义名字
     * @param receiveRetryCount  消费失败后重新放入队列中重试次数
     * @param deliveryMode       设置消息是否持久化。2-Persistent表示持久化，1-Non-persistent表示不持久化
     */
    public StoredRabbitTemplate(ConnectionFactory connectionFactory, StoredOperations storedOperations,
                                String rabbitTemplateName, int receiveRetryCount, int deliveryMode) {
        super(connectionFactory);
        this.rabbitTemplateName = rabbitTemplateName;
        this.storedOperations = storedOperations;
        this.receiveRetryCount = receiveRetryCount;
        this.deliveryMode = deliveryMode;
    }

    /**
     * RabbitMQ的confirm回调
     *
     * @param correlationData correlationData
     * @param ack             ack
     * @param error           error
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String error) {
        Object object = storedOperations.getMessageByCorrelationId(rabbitTemplateName, correlationData.getId());
        if (!ack) {
            LOGGER.info("{} 发送RabbitMQ消息 ack确认 失败: [{}], error[{}]", rabbitTemplateName, JSON.toJSONString(object), JSON.toJSONString(error));
        } else {
            LOGGER.info("{} 发送RabbitMQ消息 ack确认 成功: [{}], error[{}]", rabbitTemplateName, JSON.toJSONString(object), JSON.toJSONString(error));
            storedOperations.updateSendMessageSuccess(rabbitTemplateName, correlationData.getId());
        }
    }

    /**
     * RabbitMQ的return回调<br/>
     * 发送至Exchange却没有发送到Queue时，也会走一遍 confirm 方法
     *
     * @param message    message
     * @param code       code
     * @param error      error
     * @param exchange   exchange
     * @param routingKey routingKey
     */
    @Override
    public void returnedMessage(Message message, int code, String error, String exchange, String routingKey) {
        LOGGER.error("{} 发送RabbitMQ消息returnedMessage，出现异常，Exchange不存在或发送至Exchange却没有发送到Queue中，message：[{}], code[{}], error[{}], exchange[{}], routingKey[{}]",
                rabbitTemplateName, JSON.toJSONString(message), JSON.toJSONString(code), JSON.toJSONString(error), JSON.toJSONString(exchange), JSON.toJSONString(routingKey));
    }

    /**
     * 消费端消费失败出现异常等情况时，
     * 转发消息至Exchange为retryExchangeName中，根据RoutingKey为retryRoutingKey再次转发至原queue进行消费，
     * 如此往复3次，超过3次之后，还异常，则放入failQueue中
     *
     * @param message           message
     * @param retryExchangeName retryExchangeName
     * @param retryRoutingKey   retryRoutingKey
     * @param failExchangeName  failExchangeName
     * @param failRoutingKey    failRoutingKey
     */
    public boolean retryRabbitMQ(Message message, String retryExchangeName, String retryRoutingKey,
                                 String failExchangeName, String failRoutingKey) {
        try {
            Map<String, Object> headersMap = message.getMessageProperties().getHeaders();
            if (CollectionUtils.isEmpty(headersMap)) {
                message.getMessageProperties().setHeader(COUNT_TIME, ONE);
            }
            Integer countTime = (Integer) headersMap.getOrDefault(COUNT_TIME, ONE);
            if (countTime < receiveRetryCount) {
                message.getMessageProperties().setHeader(COUNT_TIME, ++countTime);
                // retry
                messageSendMQ(retryExchangeName, retryRoutingKey, message);
                LOGGER.info("{} rabbitmq 消费失败，重新扔回队列，exchange:[{}],routingKey:[{}],message:[{}]",
                        rabbitTemplateName, retryExchangeName, retryRoutingKey, JSON.toJSONString(message));
            } else {
                // fail
                messageSendMQ(failExchangeName, failRoutingKey, message);
                LOGGER.info("{} rabbitmq 消费失败 receiveRetryCount 次，扔到消费失败队列，exchange:[{}],routingKey:[{}],message:[{}]",
                        rabbitTemplateName, failExchangeName, failRoutingKey, JSON.toJSONString(message));
            }
        } catch (Throwable e) {
            LOGGER.error("{} rabbitmq 消费失败，重新扔回队列出现异常，exchange:[{}],routingKey:[{}],message:[{}],异常为",
                    rabbitTemplateName, retryExchangeName, retryRoutingKey, JSON.toJSONString(message), e);
            return false;
        }
        return true;
    }

    /**
     * 发送mq消息
     *
     * @param exchange   exchange
     * @param routingKey routingKey
     * @param object     object
     * @return boolean
     */
    public boolean messageSendMQ(String exchange, String routingKey, String object) {
        if (object == null) {
            return false;
        }
        // 本地缓存
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.fromInt(this.deliveryMode));
        Message message = new Message(object.getBytes(), messageProperties);
        return this.messageSendMQ(exchange, routingKey, message);
    }


    /**
     * 根据message发送消息
     *
     * @param exchange   exchange
     * @param routingKey routingKey
     * @param message    message
     * @return boolean
     */
    public boolean messageSendMQ(String exchange, String routingKey, Message message) {
        try {
            String id = UUID.randomUUID().toString();
            message.getMessageProperties().setCorrelationIdString(id);
            // 存储
            storedOperations.saveInitMessage(rabbitTemplateName, id, exchange, routingKey, message);
            // 发送消息
            this.send(exchange, routingKey, message, new CorrelationData(id));
        } catch (Throwable e) {
            LOGGER.error("{} messageSendRabbitMQ 发送异常，exchange:[{}],routingKey:[{}],object:[{}],correlationData:[{}],异常为：",
                    rabbitTemplateName, exchange, routingKey, JSON.toJSONString(message), e);
            return false;
        }
        LOGGER.info("{} messageSendRabbitMQ 发送成功，exchange:[{}],routingKey:[{}],object:[{}]", rabbitTemplateName,
                exchange, routingKey, JSON.toJSONString(message));
        return true;
    }

    public String getRabbitTemplateName() {
        return rabbitTemplateName;
    }

    public StoredOperations getStoredOperations() {
        return storedOperations;
    }
}