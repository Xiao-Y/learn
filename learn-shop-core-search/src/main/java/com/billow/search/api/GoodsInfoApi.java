package com.billow.search.api;

import com.billow.search.common.cons.EsIndexConstant;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.search.GoodsInfoSearchParam;
import com.billow.search.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品搜索相关操作
 *
 * @author liuyongtao
 * @since 2021-9-2 15:43
 */
@Slf4j
@RestController
public class GoodsInfoApi {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 条件查询
     *
     * @param param    查询参数
     * @param pageSize 页面大小
     * @param pageNo   当前页
     * @return {@link SearchHits< GoodsInfoPo>}
     * @author liuyongtao
     * @since 2021-9-6 11:37
     */
    @PostMapping("/search")
    public SearchHits<GoodsInfoPo> search(@RequestBody GoodsInfoSearchParam param,
                                          @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        SearchHits<GoodsInfoPo> searchHits = goodsInfoService.search(pageNo, pageSize, param);
        return searchHits;
    }

    @PostMapping("/update")
    public UpdateResponse update() {
        Document document = Document.create();
        document.put("brandName", "图好图好");
        return restTemplate.update(UpdateQuery.builder("1")
                        .withDocument(document).build(),
                IndexCoordinates.of(EsIndexConstant.ES_INDEX_GOODS_INFO));
    }

    @GetMapping("/searchIndex")
    public boolean searchIndex() {
        IndexOperations indexOps = restTemplate.indexOps(GoodsInfoPo.class);
        return indexOps.exists();
    }

    @DeleteMapping("/deleteIndex")
    public boolean deleteIndex() {
        IndexOperations indexOps = restTemplate.indexOps(GoodsInfoPo.class);
        return indexOps.delete();
    }

    @PutMapping("/createIndex")
    public boolean createIndex() {
        IndexOperations indexOps = restTemplate.indexOps(GoodsInfoPo.class);
        return indexOps.create();
    }

    @PutMapping("/createMapping")
    public boolean createMapping() {
        IndexOperations indexOps = restTemplate.indexOps(GoodsInfoPo.class);
        Document mapping = indexOps.createMapping(GoodsInfoPo.class);
        return indexOps.putMapping(mapping);
    }
}
