package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpuDao;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<GoodsSpuPo> findListByPage(GoodsSpuVo goodsSpuVo) {
        IPage<GoodsSpuPo> page = new Page<>(goodsSpuVo.getPageNo(), goodsSpuVo.getPageSize());
        LambdaQueryWrapper<GoodsSpuPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
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
}

