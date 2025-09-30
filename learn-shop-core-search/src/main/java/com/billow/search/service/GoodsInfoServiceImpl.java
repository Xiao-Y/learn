package com.billow.search.service;

import com.billow.aop.commons.CustomPage;
import com.billow.search.common.cons.EsIndexConstant;
import com.billow.search.common.cons.FieldNameConstant;
import com.billow.search.dao.GoodsInfoEsDao;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.search.GoodsInfoSearchParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private GoodsInfoEsDao goodsInfoEsDao;

    @Override
    public GoodsInfoPo getById(Long id) {
        return goodsInfoEsDao.selectById(id);
    }

    @Override
    public void saveOrUpdate(GoodsInfoPo goodsInfoPo) {
        goodsInfoEsDao.insert(goodsInfoPo);
    }

    @Override
    public void delById(Long id) {
        goodsInfoEsDao.deleteById(id);
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
    public CustomPage search(Integer pageNo, Integer pageSize, GoodsInfoSearchParam param) throws IOException {
        LambdaEsQueryWrapper<GoodsInfoPo> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(param.getSpuNo()), GoodsInfoPo::getSpuNo, param.getSpuNo())
                .eq(Objects.nonNull(param.getBrandId()), GoodsInfoPo::getBrandId, param.getBrandId())
                .eq(Objects.nonNull(param.getCategoryId()), GoodsInfoPo::getCategoryId, param.getCategoryId())
                .eq(Objects.nonNull(param.getNewStatus()), GoodsInfoPo::getNewStatus, param.getNewStatus())
                .eq(Objects.nonNull(param.getRecommandStatus()), GoodsInfoPo::getRecommandStatus, param.getRecommandStatus())
                .eq(Objects.nonNull(param.getPreviewStatus()), GoodsInfoPo::getPreviewStatus, param.getPreviewStatus());
        // 关键字查询
        wrapper.multiMatchQuery(StringUtils.isNotBlank(param.getKeyWorlds()),
                param.getKeyWorlds(),
                GoodsInfoPo::getKeywords,
                GoodsInfoPo::getGoodsName,
                GoodsInfoPo::getBrandName,
                GoodsInfoPo::getCategoryName,
                GoodsInfoPo::getSubTitle,
                GoodsInfoPo::getDetailTitle);
        // 添加价格范围过滤
        if (StringUtils.isNotBlank(param.getPrice())) {
            String[] split = param.getPrice().split(FieldNameConstant.FIELD_LINK_CHAR);
            Long low = StringUtils.isBlank(split[0]) ? 0L : Long.parseLong(split[0]);
            wrapper.ge(GoodsInfoPo::getPrice, low);
            if (split.length > 1 && StringUtils.isNotBlank(split[1])) {
                wrapper.le(GoodsInfoPo::getPrice, Long.parseLong(split[1]));
            }
        }
        EsPageInfo<GoodsInfoPo> pageInfo = goodsInfoEsDao.pageQuery(wrapper, pageNo, pageSize);
        return CustomPage.build()
                .setTableData(pageInfo.getList())
                .setRecordCount(pageInfo.getTotal())
                .setTotalPages(pageInfo.getPages());
    }
}
