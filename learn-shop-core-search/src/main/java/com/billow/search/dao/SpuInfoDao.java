package com.billow.search.dao;

import com.billow.search.pojo.po.SpuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SpuInfoDao extends ElasticsearchRepository<SpuInfo, Long> {
}
