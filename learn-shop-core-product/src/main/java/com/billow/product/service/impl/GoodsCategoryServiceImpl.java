package com.billow.product.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
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
        QueryWrapper qw = QueryWrapper.create()
                .eq(GoodsCategoryPo::getParentId, goodsCategoryVo.getParentId(), Objects.nonNull(goodsCategoryVo.getParentId()));
        return goodsCategoryDao.selectListByQuery(qw);
    }

    @Override
    public List<GoodsCategoryPo> findCategoryTree(Long parentId) {
        GoodsCategoryVo goodsCategoryVo = new GoodsCategoryVo();
        goodsCategoryVo.setParentId(parentId);
        return this.findList(goodsCategoryVo);
    }
}

