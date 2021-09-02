package com.billow.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSkuDao;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.dao.GoodsSpecKeyDao;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.search.GoodsSkuSearchParam;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.service.GoodsSkuService;
import com.billow.product.service.GoodsSkuSpecValueService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * sku表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Service
public class GoodsSkuServiceImpl extends HighLevelServiceImpl<GoodsSkuDao, GoodsSkuPo, GoodsSkuSearchParam> implements GoodsSkuService {

    @Autowired
    private GoodsSkuDao goodsSkuDao;
    @Autowired
    private GoodsSkuSpecValueDao goodsSkuSpecValueDao;
    @Autowired
    private GoodsSpecKeyDao goodsSpecKeyDao;
    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;
    @Autowired
    private GoodsSkuSpecValueService goodsSkuSpecValueService;

    @Override
    public List<Map<String, Object>> findGoodsSkuSpec(Long spuId) {
        List<Map<String, Object>> list = new ArrayList<>();

        LambdaQueryWrapper<GoodsSkuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuPo::getSpuId, spuId);
        List<GoodsSkuPo> goodsSkuPos = goodsSkuDao.selectList(wrapper);
        goodsSkuPos.forEach(goodsSkuPo -> {
            Map<String, Object> value = new HashMap<>();
            value.put("id", goodsSkuPo.getId().toString());
            value.put("price", goodsSkuPo.getPrice());
            value.put("stock_num", goodsSkuPo.getStock());

            // suk 对应的规格值
            LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(GoodsSkuSpecValuePo::getSkuId, goodsSkuPo.getId());
            List<GoodsSkuSpecValuePo> goodsSkuSpecValuePos = goodsSkuSpecValueDao.selectList(wrapper1);
            goodsSkuSpecValuePos.forEach(goodsSkuSpecValuePo -> {
                value.put(goodsSkuSpecValuePo.getSpecKeyId().toString(), goodsSkuSpecValuePo.getSpecValueId().toString());
            });

            list.add(value);
        });

        return list;
    }

    @Override
    public List<GoodsSkuVo> findGoodsSku(Long spuId) {

        List<GoodsSkuVo> list = new ArrayList<>();
        Map<Long, GoodsSpecKeyPo> specKey = new HashMap<>();
        Map<Long, GoodsSpecValuePo> specValue = new HashMap<>();

        LambdaQueryWrapper<GoodsSkuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuPo::getSpuId, spuId);
        List<GoodsSkuPo> goodsSkuPos = goodsSkuDao.selectList(wrapper);
        goodsSkuPos.forEach(goodsSkuPo -> {
            GoodsSkuVo goodsSkuVo = new GoodsSkuVo();
            ConvertUtils.convert(goodsSkuPo, goodsSkuVo);
            // 规格key 和 规格值
            Map<String, String> specKeyValueName = new LinkedHashMap<>();

            List<GoodsSkuSpecValueVo> skuSpecValueVos = goodsSkuVo.getGoodsSkuSpecValueVos();
            // suk 对应的规格值
            LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(GoodsSkuSpecValuePo::getSkuId, goodsSkuPo.getId());
            List<GoodsSkuSpecValuePo> goodsSkuSpecValuePos = goodsSkuSpecValueDao.selectList(wrapper1);
            goodsSkuSpecValuePos.forEach(goodsSkuSpecValuePo -> {

                // 获取规格对应的值
                GoodsSpecKeyPo goodsSpecKeyPo;
                Long specKeyId = goodsSkuSpecValuePo.getSpecKeyId();
                if (specKey.containsKey(specKeyId)) {
                    goodsSpecKeyPo = specKey.get(specKeyId);
                } else {
                    goodsSpecKeyPo = goodsSpecKeyDao.selectById(specKeyId);
                    specKey.put(specKeyId, goodsSpecKeyPo);
                }

                GoodsSpecValuePo goodsSpecValuePo;
                Long specValueId = goodsSkuSpecValuePo.getSpecValueId();
                if (specValue.containsKey(specValueId)) {
                    goodsSpecValuePo = specValue.get(specValueId);
                } else {
                    goodsSpecValuePo = goodsSpecValueDao.selectById(specValueId);
                    specValue.put(specValueId, goodsSpecValuePo);
                }
                specKeyValueName.put(goodsSpecKeyPo.getSpecName(), goodsSpecValuePo.getSpecValue());
                goodsSkuVo.setSpecKeyValueName(JSONObject.toJSONString(specKeyValueName));

                GoodsSkuSpecValueVo convert = ConvertUtils.convert(goodsSkuSpecValuePo, GoodsSkuSpecValueVo.class);
                convert.setSpecValue(goodsSpecValuePo.getSpecValue());
                skuSpecValueVos.add(convert);
            });
            list.add(goodsSkuVo);
        });


        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(GoodsSkuVo vo) {
        // 插入 sku
        GoodsSkuPo po = ConvertUtils.convert(vo, GoodsSkuPo.class);
        po.setSkuNo(NumUtil.makeNum("SK"));
//        po.setShopId("0");
        goodsSkuDao.insert(po);
        Long skuId = po.getId();
//        // 删除 sku 下的所有规格
//        LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
//        wrapper.eq(GoodsSkuSpecValuePo::getSkuId, skuId);
//        goodsSkuSpecValueDao.delete(wrapper);
        // 直接插入新的
        List<GoodsSkuSpecValuePo> goodsSkuSpecValuePos = ConvertUtils.convert(vo.getGoodsSkuSpecValueVos(), GoodsSkuSpecValuePo.class);
        for (int i = 0; i < goodsSkuSpecValuePos.size(); i++) {
            GoodsSkuSpecValuePo goodsSkuSpecValuePo = goodsSkuSpecValuePos.get(i);
            goodsSkuSpecValuePo.setSkuId(skuId);
            goodsSkuSpecValuePo.setSkuSpecSort(new Long(i));
            goodsSkuSpecValueDao.insert(goodsSkuSpecValuePo);
        }
        ConvertUtils.convert(po, vo);
        vo.setGoodsSkuSpecValueVos(ConvertUtils.convert(goodsSkuSpecValuePos, GoodsSkuSpecValueVo.class));
    }

    @Override
    public void update(GoodsSkuVo vo) {
        // 插入 sku
        GoodsSkuPo po = ConvertUtils.convert(vo, GoodsSkuPo.class);
        goodsSkuDao.updateById(po);

        Long skuId = po.getId();
        // 删除 sku 下的所有规格
        LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuSpecValuePo::getSkuId, skuId);
        goodsSkuSpecValueDao.delete(wrapper);
        // 重新添加
        List<GoodsSkuSpecValuePo> goodsSkuSpecValuePos = ConvertUtils.convert(vo.getGoodsSkuSpecValueVos(), GoodsSkuSpecValuePo.class);
        for (int i = 0; i < goodsSkuSpecValuePos.size(); i++) {
            GoodsSkuSpecValuePo goodsSkuSpecValuePo = goodsSkuSpecValuePos.get(i);
            goodsSkuSpecValuePo.setSkuId(skuId);
            goodsSkuSpecValuePo.setSkuSpecSort(new Long(i));
            goodsSkuSpecValueDao.insert(goodsSkuSpecValuePo);
        }
        ConvertUtils.convert(po, vo);
        vo.setGoodsSkuSpecValueVos(ConvertUtils.convert(goodsSkuSpecValuePos, GoodsSkuSpecValueVo.class));
    }

    @Override
    public List<Map<String, Object>> findSpuSpec(Long spuId) {
        // 返回值
        List<Map<String, Object>> tree = new ArrayList<>();
        // 查询出商品规格(key-value)
        LambdaQueryWrapper<GoodsSkuSpecValuePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GoodsSkuSpecValuePo::getSpuId, spuId)
                .eq(GoodsSkuSpecValuePo::getValidInd, true);
        List<GoodsSkuSpecValuePo> skuSpecValuePos = goodsSkuSpecValueService.list(queryWrapper);
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
        LambdaQueryWrapper<GoodsSpecKeyPo> specKeyQuery = Wrappers.lambdaQuery();
        specKeyQuery.in(GoodsSpecKeyPo::getId, specKeyIds);
        List<GoodsSpecKeyPo> goodsSpecKeyPos = goodsSpecKeyDao.selectList(specKeyQuery);
        if (CollectionUtils.isEmpty(goodsSpecKeyPos)) {
            log.warn("通过 specKeyIds{},没有查询到商品规格名!", specKeyIds);
            return tree;
        }
        // 一次全部查询后,做为局部缓存
        Map<Long, String> specNameMap = goodsSpecKeyPos.stream().collect(Collectors.toMap(m -> m.getId(), m -> m.getSpecName()));

        // 通过vlue 查询出商品规格值
        Set<Long> specValueIds = skuSpecValuePos.stream().map(m -> m.getSpecValueId()).collect(Collectors.toSet());
        LambdaQueryWrapper<GoodsSpecValuePo> specValueQuery = Wrappers.lambdaQuery();
        specValueQuery.in(GoodsSpecValuePo::getId, specValueIds);
        List<GoodsSpecValuePo> goodsSpecValuePos = goodsSpecValueDao.selectList(specValueQuery);
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
}

