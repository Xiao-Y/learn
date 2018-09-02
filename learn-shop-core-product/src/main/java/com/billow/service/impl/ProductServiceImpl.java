package com.billow.service.impl;

import com.billow.dao.ProductDao;
import com.billow.dao.ProductSpec;
import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductVo;
import com.billow.service.ProductService;
import com.billow.tools.generator.SequenceUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ToolsUtils;
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
        productVo.setDeleFlag("1");
        Page<ProductPo> productPos = productDao.findAll(ProductSpec.byProductList(productVo), pageable);
        return productPos;
    }

    @Override
    public void saveProduct(ProductVo productVo, String userCode) throws Exception {
        productVo.setId(SequenceUtil.createSequence());
        productVo.setDeleFlag("1");
        FieldUtils.setCommonFieldByInsertWithValidInd(productVo, userCode);
        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
        productDao.save(convert);
    }

    @Override
    public void updateProduct(ProductVo productVo, String userCode) throws Exception {
        FieldUtils.setCommonFieldByUpdate(productVo, userCode);
        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
        productDao.save(convert);
    }

    @Override
    public ProductVo deleteProductById(String id, String userCode) throws Exception {
        ProductPo productPo = productDao.findOne(id);
        ProductVo productVo = ConvertUtils.convert(productPo, ProductVo.class);
        productPo.setDeleFlag("0");
        FieldUtils.setCommonFieldByUpdate(productPo, userCode);
        productDao.save(productPo);
        return productVo;
    }
}
