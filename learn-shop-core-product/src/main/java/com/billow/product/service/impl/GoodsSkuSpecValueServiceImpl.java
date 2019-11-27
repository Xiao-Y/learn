package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.service.GoodsSkuSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku规格值 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSkuSpecValueServiceImpl extends ServiceImpl<GoodsSkuSpecValueDao, GoodsSkuSpecValuePo> implements GoodsSkuSpecValueService {

    @Autowired
    private GoodsSkuSpecValueDao goodsSkuSpecValueDao;

    @Override
    public IPage<GoodsSkuSpecValuePo> findListByPage(GoodsSkuSpecValueVo goodsSkuSpecValueVo) {
        IPage<GoodsSkuSpecValuePo> page = new Page<>(goodsSkuSpecValueVo.getPageNo(), goodsSkuSpecValueVo.getPageSize());
        LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSkuSpecValuePo> selectPage = goodsSkuSpecValueDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSkuSpecValuePo po = new GoodsSkuSpecValuePo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuSpecValuePo::getId, id);
        return goodsSkuSpecValueDao.update(po, wrapper) >= 1;
    }
}

