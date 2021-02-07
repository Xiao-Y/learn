package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSkuSafeguardDao;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.billow.product.pojo.vo.GoodsSkuSafeguardVo;
import com.billow.product.service.GoodsSkuSafeguardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku增值保障 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Service
public class GoodsSkuSafeguardServiceImpl extends ServiceImpl<GoodsSkuSafeguardDao, GoodsSkuSafeguardPo> implements GoodsSkuSafeguardService {

    @Autowired
    private GoodsSkuSafeguardDao goodsSkuSafeguardDao;

    @Override
    public IPage<GoodsSkuSafeguardPo> findListByPage(GoodsSkuSafeguardVo goodsSkuSafeguardVo) {
        IPage<GoodsSkuSafeguardPo> page = new Page<>(goodsSkuSafeguardVo.getPageNo(), goodsSkuSafeguardVo.getPageSize());
        LambdaQueryWrapper<GoodsSkuSafeguardPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSkuSafeguardPo> selectPage = goodsSkuSafeguardDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = goodsSkuSafeguardDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSkuSafeguardPo po = new GoodsSkuSafeguardPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSkuSafeguardPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuSafeguardPo::getId, id);
        return goodsSkuSafeguardDao.update(po, wrapper) >= 1;
    }
}

