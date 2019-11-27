package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSkuDao;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.dao.GoodsSpuSpecDao;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.service.GoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Map<String, Object>> findGoodsSku(String spuId) {
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
}

