package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.ProductDao;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductSkuVo;
import com.billow.product.pojo.vo.ProductVo;
import com.billow.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductPo> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public IPage<ProductPo> findListByPage(ProductVo productVo) {
        IPage<ProductPo> page = new Page<>(productVo.getPageNo(), productVo.getPageSize());
        LambdaQueryWrapper<ProductPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<ProductPo> selectPage = productDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        ProductPo po = new ProductPo();
        po.setValidInd(false);
        LambdaQueryWrapper<ProductPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductPo::getId, id);
        return productDao.update(po, wrapper) >= 1;
    }

    @Override
    public ProductSkuVo findProductSku(String id) {
        return null;
    }
}

