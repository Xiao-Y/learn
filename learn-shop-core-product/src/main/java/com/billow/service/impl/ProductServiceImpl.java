package com.billow.service.impl;

import com.billow.common.base.DefaultSpec;
import com.billow.dao.ProductDao;
import com.billow.dao.ProductSpec;
import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductVo;
import com.billow.service.ProductService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Page<ProductPo> findProductList(ProductVo productVo) {
        Pageable pageable = new PageRequest(productVo.getPageNo(), productVo.getPageSize());
//        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
//        DefaultSpec<ProductPo> defaultSpec = new DefaultSpec<>(convert);
        Page<ProductPo> productPos = productDao.findAll(ProductSpec.byProductList(productVo), pageable);
        return productPos;
    }
}
