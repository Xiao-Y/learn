package com.billow.mq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shuai on 2019/5/26.
 */
public class MyStoredRabbitOperationsByCache implements MyStoredRabbitOperations {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyStoredRabbitOperationsByCache.class);

    /**
     * 缓存
     */
    private Map<String, MessageWithTime> map = new ConcurrentHashMap<>();

    /**
     * 4s时间间隔
     */
    private final static Long VALID_TIME = 4000l;

    /**
     * 插入已经发送的mq消息
     *
     * @param correlationId
     * @param exchange
     * @param routingKey
     * @param message
     */
    @Override
    public void saveMessage(String name, String correlationId, String exchange, String routingKey, Message message) {
        MessageWithTime messageWithTime = new MessageWithTime(System.currentTimeMillis(), exchange, routingKey, message);
        map.put(correlationId, messageWithTime);
    }

    /**
     * 消息发送成功
     *
     * @param correlationId correlationId
     */
    @Override
    public void updateSendMessageSuccess(String name, String correlationId) {
        map.remove(correlationId);
    }

    /**
     * 获取需要重试的mq消息
     *
     * @return
     */
    @Override
    public List<MessageWithTime> getSendFailMessages(String name) {
        Set<String> set = map.keySet();
        Long now = System.currentTimeMillis();
        List<MessageWithTime> list = new ArrayList<>();
        for (String key : set) {
            MessageWithTime messageWithTime = map.get(key);
            if (null != messageWithTime && messageWithTime.getTime() != null) {
                Long messageWithTimeTime = messageWithTime.getTime();
                if (messageWithTimeTime < (now - VALID_TIME)) {
                    // 4秒钟之前的数据进行重试
                    list.add(messageWithTime);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            LOGGER.info("{} RabbitMQ 获取发送失败，重新发送的数据为[{}]", name, JSON.toJSONString(list));
        }
        return list;
    }

    /**
     * 根据correlationId获取mq消息
     *
     * @param correlationId
     * @return
     */
    @Override
    public MessageWithTime getMessageByCorrelationId(String name, String correlationId) {
        return map.get(correlationId);
    }

}