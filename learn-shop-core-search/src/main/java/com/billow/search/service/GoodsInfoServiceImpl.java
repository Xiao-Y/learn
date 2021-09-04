package com.billow.search.service;

import com.billow.search.common.cons.EsIndexConstant;
import com.billow.search.dao.GoodsInfoDao;
import com.billow.search.pojo.po.GoodsInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * 商品服务类
 *
 * @author liuyongtao
 * @since 2021-9-2 20:04
 */
@Slf4j
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private static final int size = 3000;

    @Autowired
    private GoodsInfoDao goodsInfoDao;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public GoodsInfoPo getById(Long id) {
        return goodsInfoDao.findById(id).orElse(new GoodsInfoPo());
    }

    @Override
    public void saveOrUpdate(GoodsInfoPo goodsInfoPo) {
        goodsInfoDao.save(goodsInfoPo);
    }

    @Override
    public Page<GoodsInfoPo> findIdByPage(GoodsInfoPo goodsInfoPo) {
        return goodsInfoDao.searchSimilar(goodsInfoPo, new String[]{"id"}, PageRequest.of(0, size));
    }

    @Override
    public void delById(Long id) {
        goodsInfoDao.deleteById(id);
    }

    @Override
    public long updateByCondition(Map<String, Object> queryCondition, Map<String, Object> updateVal) throws Exception {
        UpdateByQueryRequest request = new UpdateByQueryRequest(EsIndexConstant.ES_INDEX_GOODS_INFO);
        // 版本冲突
        request.setConflicts("proceed");
        //设置更新完成后刷新索引 ps很重要如果不加可能数据不会实时刷新
        request.setRefresh(true);
        // 构建查询条件
        Set<String> queryKey = queryCondition.keySet();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (String key : queryKey) {
            // //查询条件如果是and关系使用must 如何是or关系使用should
            queryBuilder.must(QueryBuilders.termQuery(key, queryCondition.get(key)));
        }
        // 查询条件
        request.setQuery(queryBuilder);
        StringBuilder script = new StringBuilder();
        Set<String> updateValKey = updateVal.keySet();
        for (String key : updateValKey) {
            script.append("ctx._source['" + key + "']='").append(updateVal.get(key)).append("';");
        }
        request.setScript(new Script(script.toString()));
        BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
        log.info("影响的条数:{}", bulkByScrollResponse.getUpdated());
        return bulkByScrollResponse.getUpdated();
    }

}
