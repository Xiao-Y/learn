package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsCategoryDao;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.search.GoodsCategorySearchParam;
import com.billow.product.pojo.vo.GoodsCategoryVo;
import com.billow.product.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
public class GoodsCategoryServiceImpl extends HighLevelServiceImpl<GoodsCategoryDao, GoodsCategoryPo, GoodsCategorySearchParam> implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Override
    public List<GoodsCategoryPo> findList(GoodsCategoryVo goodsCategoryVo) {
        LambdaQueryWrapper<GoodsCategoryPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(goodsCategoryVo.getParentId()), GoodsCategoryPo::getParentId, goodsCategoryVo.getParentId());
        return goodsCategoryDao.selectList(wrapper);
    }

    @Override
    public List<GoodsCategoryPo> findCategoryTree(Long parentId) {
        GoodsCategoryVo goodsCategoryVo = new GoodsCategoryVo();
        goodsCategoryVo.setParentId(parentId);
        return this.findList(goodsCategoryVo);
    }
}

