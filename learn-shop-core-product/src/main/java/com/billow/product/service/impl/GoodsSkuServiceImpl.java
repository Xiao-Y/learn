package com.billow.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSkuDao;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.dao.GoodsSpecKeyDao;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.service.GoodsSkuService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuDao, GoodsSkuPo> implements GoodsSkuService {

    @Autowired
    private GoodsSkuDao goodsSkuDao;
    @Autowired
    private GoodsSkuSpecValueDao goodsSkuSpecValueDao;
    @Autowired
    private GoodsSpecKeyDao goodsSpecKeyDao;
    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;

    @Override
    public IPage<GoodsSkuPo> findListByPage(GoodsSkuVo goodsSkuVo) {
        IPage<GoodsSkuPo> page = new Page<>(goodsSkuVo.getPageNo(), goodsSkuVo.getPageSize());
        LambdaQueryWrapper<GoodsSkuPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSkuPo> selectPage = goodsSkuDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSkuPo po = new GoodsSkuPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSkuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuPo::getId, id);
        return goodsSkuDao.update(po, wrapper) >= 1;
    }

    @Override
    public List<Map<String, Object>> findGoodsSkuSpec(String spuId) {
        List<Map<String, Object>> list = new ArrayList<>();

        LambdaQueryWrapper<GoodsSkuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuPo::getSpuId, spuId);
        List<GoodsSkuPo> goodsSkuPos = goodsSkuDao.selectList(wrapper);
        goodsSkuPos.forEach(goodsSkuPo -> {
            Map<String, Object> value = new HashMap<>();
            value.put("id", goodsSkuPo.getId());
            value.put("price", goodsSkuPo.getPrice());
            value.put("stock_num", goodsSkuPo.getStock());

            // suk 对应的规格值
            LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(GoodsSkuSpecValuePo::getSkuId, goodsSkuPo.getId());
            List<GoodsSkuSpecValuePo> goodsSkuSpecValuePos = goodsSkuSpecValueDao.selectList(wrapper1);
            goodsSkuSpecValuePos.forEach(goodsSkuSpecValuePo -> {
                value.put(goodsSkuSpecValuePo.getSpecKeyId(), goodsSkuSpecValuePo.getSpecValueId());
            });

            list.add(value);
        });

        return list;
    }

    @Override
    public List<GoodsSkuVo> findGoodsSku(String spuId) {

        List<GoodsSkuVo> list = new ArrayList<>();
        Map<String, GoodsSpecKeyPo> specKey = new HashMap<>();
        Map<String, GoodsSpecValuePo> specValue = new HashMap<>();

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
                String specKeyId = goodsSkuSpecValuePo.getSpecKeyId();
                if (specKey.containsKey(specKeyId)) {
                    goodsSpecKeyPo = specKey.get(specKeyId);
                } else {
                    goodsSpecKeyPo = goodsSpecKeyDao.selectById(specKeyId);
                    specKey.put(specKeyId, goodsSpecKeyPo);
                }

                GoodsSpecValuePo goodsSpecValuePo;
                String specValueId = goodsSkuSpecValuePo.getSpecValueId();
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
        po.setShopId("0");
        goodsSkuDao.insert(po);
        String skuId = po.getId();
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

        String skuId = po.getId();
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
}

