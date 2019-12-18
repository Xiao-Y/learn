package com.billow.mq.service;

import com.billow.mq.po.PublisherPo;

import java.util.List;

public interface PublisherOper {

    void save(PublisherPo publisherPo);

    void update(PublisherPo publisherPo);

    List<PublisherPo> findAll(PublisherPo publisherPo);
}
