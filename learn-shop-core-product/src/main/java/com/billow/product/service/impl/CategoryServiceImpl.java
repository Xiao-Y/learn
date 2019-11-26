package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.CategoryDao;
import com.billow.product.pojo.po.CategoryPo;
import com.billow.product.pojo.vo.CategoryVo;
import com.billow.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<ProductDao, CategoryPo> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public IPage<CategoryPo> findListByPage(CategoryVo categoryVo) {
        IPage<CategoryPo> page = new Page<>(categoryVo.getPageNo(), categoryVo.getPageSize());
        LambdaQueryWrapper<CategoryPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<CategoryPo> selectPage = categoryDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        CategoryPo po = new CategoryPo();
        po.setValidInd(false);
        LambdaQueryWrapper<CategoryPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CategoryPo::getId, id);
        return categoryDao.update(po, wrapper) >= 1;
    }
}

