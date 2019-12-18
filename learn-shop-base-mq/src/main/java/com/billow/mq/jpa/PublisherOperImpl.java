package com.billow.mq.jpa;

import com.billow.mq.po.PublisherPo;
import com.billow.mq.service.PublisherOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-12-18 17:53
 */
public class PublisherOperImpl implements PublisherOper {

    @Autowired
    private PublisherDao publisherDao;

    @Override
    public void save(PublisherPo publisherPo) {
        publisherDao.save(publisherPo);
    }

    @Override
    public void update(PublisherPo publisherPo) {
        publisherDao.save(publisherPo);
    }

    @Override
    public List<PublisherPo> findAll(PublisherPo publisherPo) {
        Example example = Example.of(publisherPo);
        List<PublisherPo> publisherPos = publisherDao.findAll(example);
        return publisherPos;
    }
}
