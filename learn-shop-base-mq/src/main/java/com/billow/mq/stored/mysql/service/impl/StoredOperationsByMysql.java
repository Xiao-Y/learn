package com.billow.mq.stored.mysql.service.impl;

import com.alibaba.fastjson.JSON;
import com.billow.mq.MessageWithTime;
import com.billow.mq.enums.PublisherStatusEnum;
import com.billow.mq.service.StoredOperations;
import com.billow.mq.stored.mysql.dao.PublisherDao;
import com.billow.mq.stored.mysql.po.PublisherPo;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shuai on 2019/5/26.
 */
@Component
public class StoredOperationsByMysql implements StoredOperations {

    @Autowired
    private PublisherDao publisherDao;

    @Override
    public void saveInitMessage(String name, String correlationId, String exchange, String routingKey, Message message) {
        // sql .... insert mq
        PublisherPo publisherPo = new PublisherPo();
        publisherPo.setExchangeName(exchange);
        publisherPo.setMessage(JSON.toJSONString(message));
        publisherPo.setRabbitTemplateName(name);
        publisherPo.setRoutingKey(routingKey);
        publisherPo.setBody(new String(message.getBody()));
        publisherPo.setStatus(PublisherStatusEnum.INIT.getStatus());
        publisherPo.setCorrelationId(correlationId);
        publisherPo.setTryCount(0);
        publisherPo.setNextRetry(new Date(new Date().getTime() + 10000));
        publisherPo.setCreateTime(new Date());
        publisherPo.setUpdateTime(new Date());
        publisherDao.save(publisherPo);

    }

    @Override
    public void updateSendMessageSuccess(String name, String correlationId) {
        // sql .... update mq success
        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setCorrelationId(correlationId);
        publisherQuery.setRabbitTemplateName(name);
        Example example = Example.of(publisherQuery);
        List<PublisherPo> list = publisherDao.findAll(example);
        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
            return;
        }
        PublisherPo mqPublisher = list.get(0);
        mqPublisher.setStatus(PublisherStatusEnum.SUCCESS.getStatus());
        publisherDao.save(mqPublisher);
    }

    @Override
    public void updateSendMessageFail(String name, String correlationId) {
        // sql .... update mq fail
        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setCorrelationId(correlationId);
        publisherQuery.setRabbitTemplateName(name);
        Example example = Example.of(publisherQuery);
        List<PublisherPo> list = publisherDao.findAll(example);
        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
            return;
        }
        PublisherPo publisher = list.get(0);
        publisher.setStatus(PublisherStatusEnum.FAIL.getStatus());
        publisherDao.save(publisher);
    }

    @Override
    public void updateTryCountAddOne(String name, String correlationId) {
        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setCorrelationId(correlationId);
        publisherQuery.setRabbitTemplateName(name);
        Example example = Example.of(publisherQuery);
        List<PublisherPo> list = publisherDao.findAll(example);
        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
            return;
        }
        PublisherPo publisher = list.get(0);
        publisher.setTryCount(publisher.getTryCount() + 1);
        publisherDao.save(publisher);
    }

    @Override
    public MessageWithTime getMessageByCorrelationId(String name, String correlationId) {
        // sql .... select mq by PublisherStatusEnum
        PublisherPo mqPublisherQuery = new PublisherPo();
        mqPublisherQuery.setCorrelationId(correlationId);
        mqPublisherQuery.setRabbitTemplateName(name);
        Example example = Example.of(mqPublisherQuery);
        List<PublisherPo> list = publisherDao.findAll(example);
        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
            return null;
        }
        PublisherPo mqPublisher = list.get(0);
        Message message = JSON.parseObject(mqPublisher.getMessage(), Message.class);
        return new MessageWithTime(mqPublisher.getTryCount(), mqPublisher.getExchangeName(), mqPublisher.getRoutingKey(), message);
    }

//    @Override
//    public List<MessageWithTime> getSendFailMessages(String name) {
//        // 查询4秒前，未发送成功的mq数据返回
//        // sql .... select all fail mq between time1 and time2
//        List<MessageWithTime> list = new ArrayList<>();
//        PublisherPo mqPublisherQuery = new PublisherPo();
//        mqPublisherQuery.setStatus(PublisherStatusEnum.INIT.getStatus());
//        mqPublisherQuery.setRabbitTemplateName(name);
//        Example example = Example.of(mqPublisherQuery);
//        List<PublisherPo> mqPublisherList = publisherDao.findAll(example);
//        for (PublisherPo mqPublisher : mqPublisherList) {
//            Message message = JSON.parseObject(mqPublisher.getMessage(), Message.class);
//            MessageWithTime messageWithTime = new MessageWithTime(mqPublisher.getTryCount(), mqPublisher.getExchangeName(), mqPublisher.getRoutingKey(), message);
//            list.add(messageWithTime);
//        }
//        return list;
//    }

    @Override
    public List<MessageWithTime> findRetrySendMessage(String name) {
        // sql .... select all fail mq between time1 and time2

        List<MessageWithTime> list = new ArrayList<>();
        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setStatus(PublisherStatusEnum.INIT.getStatus());
        publisherQuery.setRabbitTemplateName(name);
        Example example = Example.of(publisherQuery);
        List<PublisherPo> publisherPos = publisherDao.findAll(example);
        for (PublisherPo publisherPo : publisherPos) {
            // 获取超时的失败消息
            if (publisherPo.getNextRetry().getTime() < new Date().getTime()) {
                Message message = JSON.parseObject(publisherPo.getMessage(), Message.class);
                MessageWithTime messageWithTime = new MessageWithTime(publisherPo.getTryCount(), publisherPo.getExchangeName(), publisherPo.getRoutingKey(), message);
                list.add(messageWithTime);
            }
        }
        return list;
    }
}
