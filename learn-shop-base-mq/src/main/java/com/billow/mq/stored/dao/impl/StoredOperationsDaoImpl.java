package com.billow.mq.stored.dao.impl;

import com.billow.mq.stored.po.PublisherPo;
import com.billow.mq.stored.dao.StoredOperationsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认实现消息操作记录
 *
 * @author liuyongtao
 * @create 2019-12-18 17:53
 */
public class StoredOperationsDaoImpl implements StoredOperationsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(PublisherPo publisherPo) {
        String sql = "insert into mq_publisher(exchange_name,message,rabbit_template_name,routing_key,body,status," +
                "correlation_id,try_count,next_retry,create_time,update_time) value(?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, publisherPo.getExchangeName(),
                publisherPo.getMessage(),
                publisherPo.getRabbitTemplateName(),
                publisherPo.getRoutingKey(),
                publisherPo.getBody(),
                publisherPo.getStatus(),
                publisherPo.getCorrelationId(),
                publisherPo.getTryCount(),
                publisherPo.getNextRetry(),
                publisherPo.getCreateTime(),
                publisherPo.getUpdateTime());
    }

    @Override
    public void update(PublisherPo publisherPo) {
        String sql = "update mq_publisher set exchange_name = ?,message = ?,rabbit_template_name = ?,routing_key = ?," +
                "body = ?,status = ?, correlation_id = ?,try_count = ?,next_retry = ?,create_time = ?,update_time = ?" +
                "where id = ?";
        jdbcTemplate.update(sql, publisherPo.getExchangeName(),
                publisherPo.getMessage(),
                publisherPo.getRabbitTemplateName(),
                publisherPo.getRoutingKey(),
                publisherPo.getBody(),
                publisherPo.getStatus(),
                publisherPo.getCorrelationId(),
                publisherPo.getTryCount(),
                publisherPo.getNextRetry(),
                publisherPo.getCreateTime(),
                publisherPo.getUpdateTime(),
                publisherPo.getId());
    }

    @Override
    public List<PublisherPo> findAll(PublisherPo publisherPo) {
        String sql = "select id,body,correlation_id as correlationId,create_time as createTime,exchange_name as exchangeName," +
                "message,rabbit_template_name as rabbitTemplateName, routing_key as routingKey, status," +
                "update_time as updateTime,next_retry as nextRetry,try_count as tryCount from mq_publisher where 1=1";

        List<Object> query = new ArrayList<>();

        if (publisherPo.getCorrelationId() != null && !"".equals(publisherPo.getCorrelationId())) {
            sql += " and correlation_id = ? ";
            query.add(publisherPo.getCorrelationId());
        }
        if (publisherPo.getRabbitTemplateName() != null && !"".equals(publisherPo.getRabbitTemplateName())) {
            sql += " and rabbit_template_name = ? ";
            query.add(publisherPo.getRabbitTemplateName());
        }
        if (publisherPo.getStatus() != null && !"".equals(publisherPo.getStatus())) {
            sql += " and status = ? ";
            query.add(publisherPo.getStatus());
        }

        Object[] objects = query.toArray(new Object[query.size()]);
        List<PublisherPo> publisherPos = jdbcTemplate.query(sql, objects, new BeanPropertyRowMapper<>(PublisherPo.class));
        return publisherPos;
    }
}
