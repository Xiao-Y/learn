package com.billow.search.api;

import com.billow.search.dao.GoodsInfoDao;
import com.billow.search.pojo.po.GoodsInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author liuyongtao
 * @since 2021-9-2 15:43
 */
@Slf4j
@RestController
public class GoodsInfoApi {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @PostMapping("/search")
    public Iterable<GoodsInfoPo> search(GoodsInfoPo goodsInfoPo) {
        Iterable<GoodsInfoPo> all = goodsInfoDao.findAll();
        return all;
    }

    @PostMapping("/save")
    public GoodsInfoPo save() {
        GoodsInfoPo goodsInfoPo = new GoodsInfoPo();
        goodsInfoPo.setSpuId(1L);
        goodsInfoPo.setSpuNo("SPU111");
        goodsInfoPo.setGoodsName("有效的Java");
        goodsInfoPo.setBrandId(1L);
        goodsInfoPo.setBrandName("图好");
        goodsInfoPo.setCategoryId(1L);
        goodsInfoPo.setCategoryName("书籍");
        goodsInfoPo.setSubTitle("一本好书");
        goodsInfoPo.setKeywords("书，JAVA，编程，软件");
        goodsInfoPo.setDetailTitle("书（拼音：shū），是汉语通用规范一级字 [1]  。最早见于甲骨文 [2]  。本义作动词，是书写、记述的意思；后引申为名词，指简册、典籍、文书、信函等。");
        goodsInfoPo.setDescription("相信这本书出现在第一位，不会有任何意外。约书亚·布洛赫的《有效Java》是有史以来最好的Java书籍。对于任何经验级别的Java程序员来说，这绝对是一本必读的书。你会学到很多关于Java和它的API的知识");
        goodsInfoPo.setUpdateTime(new Date());
        goodsInfoPo.setCreateTime(new Date());
        goodsInfoPo.setPic("123");
        goodsInfoPo.setLowPrice(123);
        goodsInfoPo.setLowStock(12343L);
        return goodsInfoDao.save(goodsInfoPo);
    }

    @PostMapping("/update")
    public UpdateResponse update() {
        Document document = Document.create();
        document.put("brandName", "图好图好");
        return restTemplate.update(UpdateQuery.builder("1")
                        .withDocument(document).build(),
                IndexCoordinates.of("goods_info"));
    }
}
