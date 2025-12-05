package com.billow.product.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSpecKeyDao;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.dao.GoodsSpuSpecDao;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.search.GoodsSpuSpecSearchParam;
import com.billow.product.service.GoodsSkuSpecValueService;
import com.billow.product.service.GoodsSpuSpecService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * spu规格表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Service
public class GoodsSpuSpecServiceImpl extends HighLevelServiceImpl<GoodsSpuSpecDao, GoodsSpuSpecPo,GoodsSpuSpecSearchParam> implements GoodsSpuSpecService {

    @Autowired
    private GoodsSpuSpecDao goodsSpuSpecDao;
    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;
    @Autowired
    private GoodsSpecKeyDao goodsSpecKeyDao;
    @Autowired
    private GoodsSkuSpecValueService goodsSkuSpecValueService;

    @Override
    public List<Map<String, Object>> findSpuSpec(Long spuId) {
        // 返回值
        List<Map<String, Object>> tree = new ArrayList<>();
        // 查询出商品规格(key-value)
        QueryWrapper qw = QueryWrapper.create()
                .eq(GoodsSkuSpecValuePo::getSpuId, spuId)
                .eq(GoodsSkuSpecValuePo::getValidInd, true);
        List<GoodsSkuSpecValuePo> skuSpecValuePos = goodsSkuSpecValueService.list(qw);
        if (CollectionUtils.isEmpty(skuSpecValuePos)) {
            log.warn("通过 spuId{},没有查询到商品规格!", spuId);
            return tree;
        }
        // 对 SpecKeyId 分组
        Map<Long, List<GoodsSkuSpecValuePo>> collect = skuSpecValuePos.stream()
                .map(m -> {
                    GoodsSkuSpecValuePo po = new GoodsSkuSpecValuePo();
                    po.setSpecKeyId(m.getSpecKeyId());
                    po.setSpecValueId(m.getSpecValueId());
                    return po;
                })
                .collect(Collectors.groupingBy(g -> g.getSpecKeyId(), Collectors.toList()));

        // 通过key 查询出商品规格名
        Set<Long> specKeyIds = skuSpecValuePos.stream().map(m -> m.getSpecKeyId()).collect(Collectors.toSet());
        QueryWrapper qw2 = QueryWrapper.create()
                .in(GoodsSpecKeyPo::getId, specKeyIds);
        List<GoodsSpecKeyPo> goodsSpecKeyPos = goodsSpecKeyDao.selectListByQuery(qw2);
        if (CollectionUtils.isEmpty(goodsSpecKeyPos)) {
            log.warn("通过 specKeyIds{},没有查询到商品规格名!", specKeyIds);
            return tree;
        }
        // 一次全部查询后,做为局部缓存
        Map<Long, String> specNameMap = goodsSpecKeyPos.stream().collect(Collectors.toMap(m -> m.getId(), m -> m.getSpecName()));

        // 通过vlue 查询出商品规格值
        Set<Long> specValueIds = skuSpecValuePos.stream().map(m -> m.getSpecValueId()).collect(Collectors.toSet());
        QueryWrapper qw3 = QueryWrapper.create()
                .in(GoodsSpecValuePo::getId, specValueIds);
        List<GoodsSpecValuePo> goodsSpecValuePos = goodsSpecValueDao.selectListByQuery(qw3);
        if (CollectionUtils.isEmpty(goodsSpecValuePos)) {
            log.warn("通过 specValueIds{},没有查询到商品规格值!", specValueIds);
            return tree;
        }
        // 一次全部查询后,做为局部缓存
        Map<Long, String> specValueMap = goodsSpecValuePos.stream().collect(Collectors.toMap(m -> m.getId(), m -> m.getSpecValue()));

        collect.entrySet().stream().forEach(s -> {
            Map<String, Object> treeVal = new HashMap<>();
            // 规格唯一标识
            treeVal.put("k_s", s.getKey().toString());
            // 规格名称
            treeVal.put("k", specNameMap.get(s.getKey()));
            // 规格参数值
            List<Map<String, String>> v = new ArrayList<>();
            // 去除重复的
            Set<Long> specValueIdSet = s.getValue().stream().map(m -> m.getSpecValueId()).collect(Collectors.toSet());
            specValueIdSet.stream().forEach(f -> {
                Map<String, String> specValueVal = new HashMap<>();
                specValueVal.put("id", f.toString());
                specValueVal.put("name", specValueMap.get(f));
                specValueVal.put("imgUrl", "https://img.yzcdn.cn/1.jpg");
                specValueVal.put("previewImgUrl", "https://img.yzcdn.cn/1p.jpg");
                v.add(specValueVal);
            });
            treeVal.put("v", v);
            tree.add(treeVal);
        });
        return tree;
    }

    @Override
    public List<Long> findSpuSpecKey(Long spuId) {
        QueryWrapper qw = QueryWrapper.create()
                .eq(GoodsSpuSpecPo::getSpuId, spuId);
        List<GoodsSpuSpecPo> goodsSpuSpecPos = goodsSpuSpecDao.selectListByQuery(qw);
        return goodsSpuSpecPos.stream().map(m -> m.getSpecKeyId()).collect(Collectors.toList());
    }
}

