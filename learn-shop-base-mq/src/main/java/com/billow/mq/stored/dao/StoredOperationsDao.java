package com.billow.mq.stored.dao;

import com.billow.mq.stored.po.PublisherPo;

import java.util.List;

public interface StoredOperationsDao {

    void save(PublisherPo publisherPo);

    void update(PublisherPo publisherPo);

    List<PublisherPo> findAll(PublisherPo publisherPo);
}
