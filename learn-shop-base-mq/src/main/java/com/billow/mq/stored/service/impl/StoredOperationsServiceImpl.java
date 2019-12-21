package com.billow.mq.stored.service.impl;

import com.alibaba.fastjson.JSON;
import com.billow.mq.stored.vo.MessageWithTime;
import com.billow.mq.enums.PublisherStatusEnum;
import com.billow.mq.stored.service.StoredOperationsService;
import com.billow.mq.stored.dao.StoredOperationsDao;
import com.billow.mq.stored.po.PublisherPo;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shuai on 2019/5/26.
 */
@Component
public class StoredOperationsServiceImpl implements StoredOperationsService {

    @Autowired
    private StoredOperationsDao storedOperationsDao;

    @Override
    public void saveInitMessage(String rabbitTemplateName, String correlationId, String exchange, String routingKey,
                                Message message, Date retryDate, Integer tryCount) {
        // sql .... insert mq
        PublisherPo publisherPo = new PublisherPo();
        publisherPo.setExchangeName(exchange);
        publisherPo.setMessage(JSON.toJSONString(message));
        publisherPo.setRabbitTemplateName(rabbitTemplateName);
        publisherPo.setRoutingKey(routingKey);
        publisherPo.setBody(new String(message.getBody()));
        publisherPo.setStatus(PublisherStatusEnum.INIT.getStatus());
        publisherPo.setCorrelationId(correlationId);
        publisherPo.setTryCount(tryCount);
        publisherPo.setNextRetry(retryDate);
        publisherPo.setCreateTime(new Date());
        publisherPo.setUpdateTime(new Date());
        storedOperationsDao.save(publisherPo);

    }

    @Override
    public void updateStautsSuccess(String rabbitTemplateName, String correlationId) {
        PublisherPo publisher = this.findMessage(rabbitTemplateName, correlationId);
        if (publisher == null) {
            return;
        }
        // sql .... update mq success
        publisher.setStatus(PublisherStatusEnum.SUCCESS.getStatus());
        publisher.setUpdateTime(new Date());
        storedOperationsDao.update(publisher);
    }

    @Override
    public void updateStautsFail(String rabbitTemplateName, String correlationId) {
        PublisherPo publisher = this.findMessage(rabbitTemplateName, correlationId);
        if (publisher == null) {
            return;
        }
        // sql .... update mq fail
        publisher.setStatus(PublisherStatusEnum.FAIL.getStatus());
        publisher.setUpdateTime(new Date());
        storedOperationsDao.update(publisher);
    }

    @Override
    public void updateNextRetry(String rabbitTemplateName, String correlationId, Date nextRetry, Integer tryCount) {
        PublisherPo publisher = this.findMessage(rabbitTemplateName, correlationId);
        if (publisher == null) {
            return;
        }
        // sql .... update mq TryCount + 1
        publisher.setTryCount(tryCount);
        publisher.setNextRetry(nextRetry);
        publisher.setUpdateTime(new Date());
        storedOperationsDao.update(publisher);
    }

    @Override
    public MessageWithTime findMessageByCorrelationId(String rabbitTemplateName, String correlationId) {
        PublisherPo publisher = this.findMessage(rabbitTemplateName, correlationId);
        if (publisher == null) {
            return null;
        }
        Message message = JSON.parseObject(publisher.getMessage(), Message.class);
        return new MessageWithTime(publisher.getTryCount(), publisher.getExchangeName(), publisher.getRoutingKey(),
                publisher.getStatus(), message);
    }

    @Override
    public List<MessageWithTime> findRetryMessage(String rabbitTemplateName) {
        // sql .... select all fail mq between time1 and time2
        List<MessageWithTime> list = new ArrayList<>();

        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setStatus(PublisherStatusEnum.INIT.getStatus());
        publisherQuery.setRabbitTemplateName(rabbitTemplateName);
        List<PublisherPo> publisherPos = storedOperationsDao.findAll(publisherQuery);
        for (PublisherPo publisherPo : publisherPos) {
            // 获取超时的失败消息
            if (publisherPo.getNextRetry().getTime() < new Date().getTime()) {
                Message message = JSON.parseObject(publisherPo.getMessage(), Message.class);
                MessageWithTime messageWithTime = new MessageWithTime(publisherPo.getTryCount(), publisherPo.getExchangeName(),
                        publisherPo.getRoutingKey(), publisherPo.getStatus(), message);
                list.add(messageWithTime);
            }
        }
        return list;
    }

    /**
     * 根据 rabbitTemplateName 和 correlationId 查询出 message
     *
     * @param rabbitTemplateName
     * @param correlationId
     * @return com.billow.mq.stored.mysql.po.PublisherPo
     * @author LiuYongTao
     * @date 2019/10/18 14:58
     */
    private PublisherPo findMessage(String rabbitTemplateName, String correlationId) {
        PublisherPo publisherQuery = new PublisherPo();
        publisherQuery.setCorrelationId(correlationId);
        publisherQuery.setRabbitTemplateName(rabbitTemplateName);
        List<PublisherPo> list = storedOperationsDao.findAll(publisherQuery);
        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
            return null;
        }
        return list.get(0);
    }
}
