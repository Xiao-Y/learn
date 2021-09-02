package com.billow.search.dao;

import com.billow.search.pojo.GoodsInfoPo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsInfoDao extends ElasticsearchRepository<GoodsInfoPo, Long> {
}
