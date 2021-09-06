package com.billow.search.service;

import com.billow.search.common.cons.EsIndexConstant;
import com.billow.search.dao.GoodsInfoDao;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.search.GoodsInfoSearchParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private GoodsInfoDao goodsInfoDao;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestTemplate template;

    @Override
    public GoodsInfoPo getById(Long id) {
        return goodsInfoDao.findById(id).orElse(new GoodsInfoPo());
    }

    @Override
    public void saveOrUpdate(GoodsInfoPo goodsInfoPo) {
        goodsInfoDao.save(goodsInfoPo);
    }

    @Override
    public void delById(Long id) {
        goodsInfoDao.deleteById(id);
    }

    @Override
    public void updateByCondition(Map<String, Object> condition, Map<String, Object> updateVle) throws IOException {
        UpdateByQueryRequest request = new UpdateByQueryRequest(EsIndexConstant.ES_INDEX_GOODS_INFO);
        // 版本冲突
        request.setConflicts("proceed");
        // 构建查询条件
        QueryBuilder queryBuilder = this.genQueryCondition(condition);
        request.setQuery(queryBuilder);
        // 构建更新 eql
        request.setScript(this.genUpdate(updateVle));
        BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
        log.info("影响的条数:{}", bulkByScrollResponse.getUpdated());
    }

    /**
     * 构建更新语句
     *
     * @param updateVle 更新值
     * @author liuyongtao
     * @since 2021-9-6 9:36
     */
    private Script genUpdate(Map<String, Object> updateVle) {
        StringBuilder script = new StringBuilder();
        Set<String> keys = updateVle.keySet();
        for (String key : keys) {
            script.append("ctx._source['").append(key).append("']='").append(updateVle.get(key)).append("';");
        }
        return new Script(script.toString());
    }

    /**
     * 构建查询条件
     *
     * @param condition 查询条件
     * @author liuyongtao
     * @since 2021-9-6 9:32
     */
    private QueryBuilder genQueryCondition(Map<String, Object> condition) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Set<String> keys = condition.keySet();
        for (String key : keys) {
            boolQueryBuilder.must(QueryBuilders.termQuery(key, condition.get(key)));
        }
        return boolQueryBuilder;
    }

    @Override
    public SearchHits<GoodsInfoPo> search(Integer pageNo, Integer pageSize, GoodsInfoSearchParam param) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 构建查询条件
        if (StringUtils.isNotBlank(param.getSpuNo())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("spuNo", param.getSpuNo()));
        }
        if (Objects.nonNull(param.getBrandId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brandId", param.getBrandId()));
        }
        if (Objects.nonNull(param.getCategoryId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryId", param.getCategoryId()));
        }
        // 关键字搜索匹配，分词
        if (StringUtils.isNotBlank(param.getKeyWorlds())) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(param.getKeyWorlds(),
                    "keywords", "goodsName", "brandName", "categoryName", "subTitle", "detailTitle"));
        }

        // 价格范围查询
        if (StringUtils.isNotBlank(param.getPrice())) {
            RangeQueryBuilder rangePrice = QueryBuilders.rangeQuery("priceRange");
            String[] split = param.getPrice().split("~");
            Long low = StringUtils.isBlank(split[0]) ? 0L : Long.parseLong(split[0]);
            rangePrice.gte(low);
            if (split.length > 1) {
                Long hig = StringUtils.isBlank(split[1]) ? null : Long.parseLong(split[1]);
                if (hig != null) {
                    rangePrice.lte(hig);
                }
            }
            boolQueryBuilder.filter(rangePrice);
        }

        // 分布条件
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);

        // 结果高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("subTitle")
                .field("detailTitle")
                .preTags("<font color='red'>")
                .postTags("</font>");

        // 组装查询
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .withHighlightBuilder(highlightBuilder)
                .build();
        return template.search(nativeSearchQuery, GoodsInfoPo.class);
    }
}
