package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsCategoryDao;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.vo.GoodsCategoryVo;
import com.billow.product.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryDao, GoodsCategoryPo> implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Override
    public IPage<GoodsCategoryPo> findListByPage(GoodsCategoryVo goodsCategoryVo) {
        IPage<GoodsCategoryPo> page = new Page<>(goodsCategoryVo.getPageNo(), goodsCategoryVo.getPageSize());
        LambdaQueryWrapper<GoodsCategoryPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsCategoryPo> selectPage = goodsCategoryDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsCategoryPo po = new GoodsCategoryPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsCategoryPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsCategoryPo::getId, id);
        return goodsCategoryDao.update(po, wrapper) >= 1;
    }

    @Override
    public List<GoodsCategoryPo> findList(GoodsCategoryVo goodsCategoryVo) {
        LambdaQueryWrapper<GoodsCategoryPo> wrapper = Wrappers.lambdaQuery();
        return goodsCategoryDao.selectList(wrapper);
    }
}

