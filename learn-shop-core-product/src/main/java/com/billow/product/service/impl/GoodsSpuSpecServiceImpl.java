package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpecKeyDao;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.dao.GoodsSpuSpecDao;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.vo.GoodsSpuSpecVo;
import com.billow.product.service.GoodsSpuSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * spu规格表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSpuSpecServiceImpl extends ServiceImpl<GoodsSpuSpecDao, GoodsSpuSpecPo> implements GoodsSpuSpecService {

    @Autowired
    private GoodsSpuSpecDao goodsSpuSpecDao;
    @Autowired
    private GoodsSpecKeyDao goodsSpecKeyDao;
    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;

    @Override
    public IPage<GoodsSpuSpecPo> findListByPage(GoodsSpuSpecVo goodsSpuSpecVo) {
        IPage<GoodsSpuSpecPo> page = new Page<>(goodsSpuSpecVo.getPageNo(), goodsSpuSpecVo.getPageSize());
        LambdaQueryWrapper<GoodsSpuSpecPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        return goodsSpuSpecDao.selectPage(page, wrapper);
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSpuSpecPo po = new GoodsSpuSpecPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSpuSpecPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpuSpecPo::getId, id);
        return goodsSpuSpecDao.update(po, wrapper) >= 1;
    }

    @Override
    public List<Map<String, Object>> findSpuSpec(String spuId) {
        List<Map<String, Object>> tree = new ArrayList<>();
        LambdaQueryWrapper<GoodsSpuSpecPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpuSpecPo::getSpuId, spuId);
        // 查询出商品规格
        List<GoodsSpuSpecPo> goodsSpuSpecPos = goodsSpuSpecDao.selectList(wrapper);
        goodsSpuSpecPos.forEach(goodsSpuSpecPo -> {
            String specKeyId = goodsSpuSpecPo.getSpecKeyId();
            Map<String, Object> treeVal = new HashMap<>();
            treeVal.put("k_s", specKeyId);
            // 查询规格名称
            GoodsSpecKeyPo goodsSpecKeyPo = goodsSpecKeyDao.selectById(specKeyId);
            treeVal.put("k", goodsSpecKeyPo.getSpecName());
            // 查询规格值
            List<Map<String, Object>> v = new ArrayList<>();
            LambdaQueryWrapper<GoodsSpecValuePo> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(GoodsSpecValuePo::getSpecKeyId, specKeyId);
            List<GoodsSpecValuePo> goodsSpecValuePos = goodsSpecValueDao.selectList(wrapper1);
            goodsSpecValuePos.forEach(goodsSpecValuePo -> {
                Map<String, Object> specValueVal = new HashMap<>();
                specValueVal.put("id", goodsSpecValuePo.getId());
                specValueVal.put("name", goodsSpecValuePo.getSpecValue());
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
    public List<String> findSpuSpecKey(String spuId) {
        LambdaQueryWrapper<GoodsSpuSpecPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpuSpecPo::getSpuId, spuId);
        List<GoodsSpuSpecPo> goodsSpuSpecPos = goodsSpuSpecDao.selectList(wrapper);
        return goodsSpuSpecPos.stream().map(m -> m.getSpecKeyId()).collect(Collectors.toList());
    }
}

