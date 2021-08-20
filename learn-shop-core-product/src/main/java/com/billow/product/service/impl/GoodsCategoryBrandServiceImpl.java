package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsCategoryBrandDao;
import com.billow.product.pojo.po.GoodsCategoryBrandPo;
import com.billow.product.pojo.vo.GoodsCategoryBrandVo;
import com.billow.product.service.GoodsCategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Service
public class GoodsCategoryBrandServiceImpl extends ServiceImpl<GoodsCategoryBrandDao, GoodsCategoryBrandPo> implements GoodsCategoryBrandService {

    @Autowired
    private GoodsCategoryBrandDao goodsCategoryBrandDao;

    @Override
    public IPage<GoodsCategoryBrandPo> findListByPage(GoodsCategoryBrandVo goodsCategoryBrandVo) {
        IPage<GoodsCategoryBrandPo> page = new Page<>(goodsCategoryBrandVo.getPageNo(), goodsCategoryBrandVo.getPageSize());
        LambdaQueryWrapper<GoodsCategoryBrandPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsCategoryBrandPo> selectPage = goodsCategoryBrandDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = goodsCategoryBrandDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsCategoryBrandPo po = new GoodsCategoryBrandPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsCategoryBrandPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsCategoryBrandPo::getId, id);
        return goodsCategoryBrandDao.update(po, wrapper) >= 1;
    }
}

