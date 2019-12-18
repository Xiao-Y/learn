package com.billow.mq.jpa;

import com.billow.mq.po.PublisherPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PublisherDao extends JpaRepository<PublisherPo, Long>, JpaSpecificationExecutor<PublisherPo> {

}
