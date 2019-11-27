package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsBrandDao;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.vo.GoodsBrandVo;
import com.billow.product.service.GoodsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsBrandServiceImpl extends ServiceImpl<GoodsBrandDao, GoodsBrandPo> implements GoodsBrandService {

    @Autowired
    private GoodsBrandDao goodsBrandDao;

    @Override
    public IPage<GoodsBrandPo> findListByPage(GoodsBrandVo goodsBrandVo) {
        IPage<GoodsBrandPo> page = new Page<>(goodsBrandVo.getPageNo(), goodsBrandVo.getPageSize());
        LambdaQueryWrapper<GoodsBrandPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsBrandPo> selectPage = goodsBrandDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsBrandPo po = new GoodsBrandPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsBrandPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsBrandPo::getId, id);
        return goodsBrandDao.update(po, wrapper) >= 1;
    }
}

