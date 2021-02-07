package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpuDetailDao;
import com.billow.product.pojo.po.GoodsSpuDetailPo;
import com.billow.product.pojo.vo.GoodsSpuDetailVo;
import com.billow.product.service.GoodsSpuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Service
public class GoodsSpuDetailServiceImpl extends ServiceImpl<GoodsSpuDetailDao, GoodsSpuDetailPo> implements GoodsSpuDetailService {

    @Autowired
    private GoodsSpuDetailDao goodsSpuDetailDao;

    @Override
    public IPage<GoodsSpuDetailPo> findListByPage(GoodsSpuDetailVo goodsSpuDetailVo) {
        IPage<GoodsSpuDetailPo> page = new Page<>(goodsSpuDetailVo.getPageNo(), goodsSpuDetailVo.getPageSize());
        LambdaQueryWrapper<GoodsSpuDetailPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSpuDetailPo> selectPage = goodsSpuDetailDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = goodsSpuDetailDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSpuDetailPo po = new GoodsSpuDetailPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSpuDetailPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpuDetailPo::getId, id);
        return goodsSpuDetailDao.update(po, wrapper) >= 1;
    }
}

