//package com.billow.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.springboot.dao.second.MqPublisherDao;
//import com.springboot.vo.MqPublisher;
//import org.springframework.amqp.core.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by shuai on 2019/5/26.
// */
//@Component
//public class MyStoredRabbitOperationsByMySql implements MyStoredRabbitOperations {
//
//    @Autowired
//    private MqPublisherDao mqPublisherDao;
//
//    @Override
//    public void saveMessage(String name, String correlationId, String exchange, String routingKey, Message message) {
//        // sql .... insert mq
//        MqPublisher mqPublisher = new MqPublisher();
//        mqPublisher.setExchangeName(exchange);
//        mqPublisher.setMessage(JSON.toJSONString(message));
//        mqPublisher.setRabbitTemplateName(name);
//        mqPublisher.setRoutingKey(routingKey);
//        mqPublisher.setBody(new String(message.getBody()));
//        mqPublisher.setStatus(MqPublisherStatusEnum.INIT.getStatus());
//        mqPublisher.setCorrelationId(correlationId);
//        mqPublisher.setCreateTime(new Date());
//        mqPublisher.setUpdateTime(new Date());
//        mqPublisherDao.insert(mqPublisher);
//
//    }
//
//    @Override
//    public void updateSendMessageSuccess(String name, String correlationId) {
//        // sql .... update mq success
//        MqPublisher mqPublisherQuery = new MqPublisher();
//        mqPublisherQuery.setCorrelationId(correlationId);
//        mqPublisherQuery.setRabbitTemplateName(name);
//        List<MqPublisher> list = mqPublisherDao.list(mqPublisherQuery);
//        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
//            return;
//        }
//        MqPublisher mqPublisher = list.get(0);
//        mqPublisher.setStatus(MqPublisherStatusEnum.SUCCESS.getStatus());
//        mqPublisherDao.updateByPrimaryKeySelective(mqPublisher);
//    }
//
//    @Override
//    public MessageWithTime getMessageByCorrelationId(String name, String correlationId) {
//        // sql .... select mq by correlationId
//        MqPublisher mqPublisherQuery = new MqPublisher();
//        mqPublisherQuery.setCorrelationId(correlationId);
//        mqPublisherQuery.setRabbitTemplateName(name);
//        List<MqPublisher> list = mqPublisherDao.list(mqPublisherQuery);
//        if (CollectionUtils.isEmpty(list) || list.size() > 1) {
//            return null;
//        }
//        MqPublisher mqPublisher = list.get(0);
//        Message message = JSON.parseObject(mqPublisher.getMessage(), Message.class);
//        return new MessageWithTime(mqPublisher.getCreateTime().getTime(), mqPublisher.getExchangeName(), mqPublisher.getRoutingKey(), message);
//    }
//
//    @Override
//    public List<MessageWithTime> getSendFailMessages(String name) {
//        // 查询4秒前，未发送成功的mq数据返回
//
//        // sql .... select all fail mq between time1 and time2
//        List<MessageWithTime> list = new ArrayList<>();
//        MqPublisher mqPublisherQuery = new MqPublisher();
//        mqPublisherQuery.setStatus(1);
//        mqPublisherQuery.setRabbitTemplateName(name);
//        List<MqPublisher> mqPublisherList = mqPublisherDao.list(mqPublisherQuery);
//        for (MqPublisher mqPublisher : mqPublisherList) {
//            Message message = JSON.parseObject(mqPublisher.getMessage(), Message.class);
//            MessageWithTime messageWithTime = new MessageWithTime(mqPublisher.getCreateTime().getTime(), mqPublisher.getExchangeName(), mqPublisher.getRoutingKey(), message);
//            list.add(messageWithTime);
//        }
//        return list;
//    }
//
//}
