package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.ProductSpcesDao;
import com.billow.product.pojo.po.ProductSpcesPo;
import com.billow.product.pojo.vo.ProductSpcesVo;
import com.billow.product.service.ProductSpcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class ProductSpcesServiceImpl extends ServiceImpl<ProductDao, ProductSpcesPo> implements ProductSpcesService {

    @Autowired
    private ProductSpcesDao productSpcesDao;

    @Override
    public IPage<ProductSpcesPo> findListByPage(ProductSpcesVo productSpcesVo) {
        IPage<ProductSpcesPo> page = new Page<>(productSpcesVo.getPageNo(), productSpcesVo.getPageSize());
        LambdaQueryWrapper<ProductSpcesPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<ProductSpcesPo> selectPage = productSpcesDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        ProductSpcesPo po = new ProductSpcesPo();
        po.setValidInd(false);
        LambdaQueryWrapper<ProductSpcesPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductSpcesPo::getId, id);
        return productSpcesDao.update(po, wrapper) >= 1;
    }
}

