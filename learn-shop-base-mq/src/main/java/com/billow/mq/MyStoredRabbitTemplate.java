package com.billow.mq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shuai on 2019/5/12.
 */
public class MyStoredRabbitTemplate extends RabbitTemplate implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyStoredRabbitTemplate.class);
    /**
     * 重试次数key
     */
    private final static String COUNT_TIME = "count_time";

    private final static Integer ONE = 1;

    private final static Integer FOUR = 4;

    /**
     * 默认的name
     */
    private final static String RABBIT_TEMPLATE_NAME = "MyStoredRabbitTemplate";

    /**
     * MyRabbitTemplate的name
     */
    private String name = RABBIT_TEMPLATE_NAME;

    /**
     * MyStoredRabbitTemplate的操作，插入，更新，数据
     */
    private MyStoredRabbitOperations myStoredRabbitOperations;

    /**
     * @param connectionFactory        rabbitmq连接工厂
     * @param myStoredRabbitOperations 重试策略
     * @param RabbitTemplateName       自定义名字
     */
    public MyStoredRabbitTemplate(ConnectionFactory connectionFactory, MyStoredRabbitOperations myStoredRabbitOperations, String RabbitTemplateName) {
        super(connectionFactory);
        this.name = RabbitTemplateName;
        this.myStoredRabbitOperations = myStoredRabbitOperations;
    }

    /**
     * RabbitMQ的confirm回调
     *
     * @param correlationData correlationData
     * @param ack             ack
     * @param s               s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        Object object = myStoredRabbitOperations.getMessageByCorrelationId(name, correlationData.getId());
        if (!ack) {
            LOGGER.info("{} 发送RabbitMQ消息 ack确认 失败: [{}]", name, JSON.toJSONString(object));
        } else {
            LOGGER.info("{} 发送RabbitMQ消息 ack确认 成功: [{}]", name, JSON.toJSONString(object));
            myStoredRabbitOperations.updateSendMessageSuccess(name, correlationData.getId());
        }
    }

    /**
     * RabbitMQ的return回调
     *
     * @param message    message
     * @param code       code
     * @param s          s
     * @param exchange   exchange
     * @param routingKey routingKey
     */
    @Override
    public void returnedMessage(Message message, int code, String s, String exchange, String routingKey) {
        LOGGER.error("{} 发送RabbitMQ消息returnedMessage，出现异常，Exchange不存在或发送至Exchange却没有发送到Queue中，message：[{}], code[{}], s[{}], exchange[{}], routingKey[{}]",
                name, JSON.toJSONString(message), JSON.toJSONString(code), JSON.toJSONString(s), JSON.toJSONString(exchange), JSON.toJSONString(routingKey));
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
    public boolean retryRabbitMQ(Message message, String retryExchangeName, String retryRoutingKey, String failExchangeName, String failRoutingKey) {
        try {
            Map<String, Object> headersMap = message.getMessageProperties().getHeaders();
            if (CollectionUtils.isEmpty(headersMap)) {
                message.getMessageProperties().setHeader(COUNT_TIME, ONE);
                // retry
                messageSendMQ(retryExchangeName, retryRoutingKey, message);
                LOGGER.info("{} rabbitmq 消费失败，重新扔回队列，exchange:[{}],routingKey:[{}],message:[{}]", name, retryExchangeName, retryRoutingKey, JSON.toJSONString(message));
            } else {
                if (!headersMap.containsKey(COUNT_TIME) || headersMap.get(COUNT_TIME) == null) {
                    headersMap.put(COUNT_TIME, ONE);
                    // retry
                    messageSendMQ(retryExchangeName, retryRoutingKey, message);
                    LOGGER.info("{} rabbitmq 消费失败，重新扔回队列，exchange:[{}],routingKey:[{}],message:[{}]", name, retryExchangeName, retryRoutingKey, JSON.toJSONString(message));
                } else {
                    Integer countTime = (Integer) headersMap.get(COUNT_TIME);
                    if (countTime < FOUR) {
                        message.getMessageProperties().setHeader(COUNT_TIME, ++countTime);
                        // retry
                        messageSendMQ(retryExchangeName, retryRoutingKey, message);
                        LOGGER.info("{} rabbitmq 消费失败，重新扔回队列，exchange:[{}],routingKey:[{}],message:[{}]", name, retryExchangeName, retryRoutingKey, JSON.toJSONString(message));
                    } else {
                        // fail
                        messageSendMQ(failExchangeName, failRoutingKey, message);
                        LOGGER.info("{} rabbitmq 消费失败4次，扔到消费失败队列，exchange:[{}],routingKey:[{}],message:[{}]", name, failExchangeName, failRoutingKey, JSON.toJSONString(message));
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.error("{} rabbitmq 消费失败，重新扔回队列出现异常，exchange:[{}],routingKey:[{}],message:[{}],异常为", name, retryExchangeName, retryRoutingKey, JSON.toJSONString(message), e);
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
    public boolean MQSend(String exchange, String routingKey, Object object) {
        try {
            if (object == null) {
                return false;
            }
            String data = JSON.toJSONString(object);
            String generateId = UUID.randomUUID().toString();
            // 本地缓存
            MessageProperties messageProperties = new MessageProperties();
            // 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
            messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            messageProperties.setCorrelationIdString(generateId);
            Message message = new Message(data.getBytes(), messageProperties);
            // 存储
            myStoredRabbitOperations.saveMessage(name, generateId, exchange, routingKey, message);

            this.send(exchange, routingKey, message, new CorrelationData(generateId));
        } catch (Throwable e) {
            LOGGER.error("{} sendRabbitMQ 发送异常，exchange:[{}],routingKey:[{}],object:[{}],correlationData:[{}],异常为：", name, exchange, routingKey, JSON.toJSONString(object), e);
            return false;
        }
        LOGGER.info("{} sendRabbitMQ 发送成功，exchange:[{}],routingKey:[{}],object:[{}]", name, exchange, routingKey, JSON.toJSONString(object));
        return true;
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
            myStoredRabbitOperations.saveMessage(name, id, exchange, routingKey, message);

            this.send(exchange, routingKey, message, new CorrelationData(id));
        } catch (Throwable e) {
            LOGGER.error("{} messageSendRabbitMQ 发送异常，exchange:[{}],routingKey:[{}],object:[{}],correlationData:[{}],异常为：", name, exchange, routingKey, JSON.toJSONString(message), e);
            return false;
        }
        LOGGER.info("{} messageSendRabbitMQ 发送成功，exchange:[{}],routingKey:[{}],object:[{}]", name, exchange, routingKey, JSON.toJSONString(message));
        return true;
    }


    /**
     * 缓存阈值，报警
     */
    private Integer CACHE_THRESHOLD = 100;

    /**
     * 启动时是否休眠
     */
    private boolean isSleep = true;

    /**
     * 休眠多长时间
     */
    private Integer DEFAULT_RECONNECTION_DELAY = 1000;

    /**
     * 时间间隔4秒
     */
    private int VALID_TIME = 4000;
    /**
     * 执行间隔
     */
    private int RETRY_TIME_INTERVAL = 10;
    /**
     * 开关
     */
    private boolean stop = false;

    /**
     * rabbitmq发送补偿
     */
    @PostConstruct
    private void startRetry() {
        new Thread(() -> {
            while (!stop) {
                try {
                    // 启动时先暂停3s
                    if (isSleep) {
                        Thread.sleep(DEFAULT_RECONNECTION_DELAY);
                        isSleep = false;
                    }
                    // 每次都停
                    Thread.sleep(RETRY_TIME_INTERVAL);
                    List<MessageWithTime> list = myStoredRabbitOperations.getSendFailMessages(name);
                    if (CollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    long now = System.currentTimeMillis();
                    int count = 0;
                    for (MessageWithTime messageWithTime : list) {
                        Long messageWithTimeTime = messageWithTime.getTime();
                        if (messageWithTimeTime < (now - VALID_TIME)) {
                            ++count;
                            String correlationId = messageWithTime.getMessage().getMessageProperties().getCorrelationIdString();
                            if (correlationId == null || "".equals(correlationId)) {
                                LOGGER.error("{} RabbitMQ 补偿发送，发现mq消息格式不正确,correlationId为空,数据为[{}]", name, JSON.toJSONString(messageWithTime));
                                continue;
                            }
                            // 4秒钟之前的数据进行重试
                            this.send(messageWithTime.getExchange(), messageWithTime.getRoutingKey(), messageWithTime.getMessage(), new CorrelationData(correlationId));
                            LOGGER.info("{} RabbitMQ 补偿发送, messageWithTime:[{}]", name, JSON.toJSONString(messageWithTime));
                        }
                    }
                    if (count > CACHE_THRESHOLD) {
                        LOGGER.error("{} RabbitMQ 补偿发送，发现积压消息过多,此次共重试了[{}]条mq消息", name, count);
                    }
                } catch (Throwable e) {
                    LOGGER.error("{} RabbitMQ 补偿发送 线程暂停出现异常：", name, e);
                }
            }
        }).start();
    }
}