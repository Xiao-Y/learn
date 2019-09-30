package com.billow.mq.stored.mysql.dao;

import com.billow.mq.stored.mysql.po.PublisherPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PublisherDao extends JpaRepository<PublisherPo, Long>, JpaSpecificationExecutor<PublisherPo> {

}
