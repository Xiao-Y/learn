package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpuDao;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSpuService;
import com.billow.product.service.GoodsSpuSpecService;
import com.billow.tools.generator.OrderNumUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSpuServiceImpl extends ServiceImpl<GoodsSpuDao, GoodsSpuPo> implements GoodsSpuService {

    @Autowired
    private GoodsSpuDao goodsSpuDao;
    @Autowired
    private GoodsSpuSpecService goodsSpuSpecService;

    @Override
    public IPage<GoodsSpuPo> findListByPage(GoodsSpuVo goodsSpuVo) {
        IPage<GoodsSpuPo> page = new Page<>(goodsSpuVo.getPageNo(), goodsSpuVo.getPageSize());
        LambdaQueryWrapper<GoodsSpuPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        wrapper.eq(ToolsUtils.isNotEmpty(goodsSpuVo.getSpuNo()), GoodsSpuPo::getSpuNo, goodsSpuVo.getSpuNo())
                .like(ToolsUtils.isNotEmpty(goodsSpuVo.getGoodsName()), GoodsSpuPo::getGoodsName, goodsSpuVo.getGoodsName())
                .eq(ToolsUtils.isNotEmpty(goodsSpuVo.getBrandId()), GoodsSpuPo::getBrandId, goodsSpuVo.getBrandId())
                .eq(ToolsUtils.isNotEmpty(goodsSpuVo.getCategoryId()), GoodsSpuPo::getCategoryId, goodsSpuVo.getCategoryId());
        IPage<GoodsSpuPo> selectPage = goodsSpuDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSpuPo po = new GoodsSpuPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSpuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpuPo::getId, id);
        return goodsSpuDao.update(po, wrapper) >= 1;
    }

    @Override
    public void addOrUpdate(GoodsSpuVo goodsSpuVo) {
        // 保存更新商品信息
        GoodsSpuPo po = ConvertUtils.convert(goodsSpuVo, GoodsSpuPo.class);
        String id = goodsSpuVo.getId();
        if (ToolsUtils.isEmpty(id)) {
            po.setSpuNo(OrderNumUtil.makeOrderNum("PG"));
        }
        this.saveOrUpdate(po);

        if (ToolsUtils.isNotEmpty(id)) {
            LambdaQueryWrapper<GoodsSpuSpecPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(GoodsSpuSpecPo::getSpuId, id);
            goodsSpuSpecService.remove(wrapper);
        }

        // 保存商品规格信息
        List<String> specKeys = goodsSpuVo.getSpecKeys();
        specKeys.forEach(f -> {
            GoodsSpuSpecPo spuSpecPo = new GoodsSpuSpecPo();
            spuSpecPo.setSpuId(id);
            spuSpecPo.setSpecKeyId(f);
            goodsSpuSpecService.save(spuSpecPo);
        });

        ConvertUtils.convert(po, goodsSpuVo);
    }

}

