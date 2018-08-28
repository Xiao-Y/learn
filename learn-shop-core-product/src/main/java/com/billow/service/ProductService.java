package com.billow.service;

import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductVo;
import org.springframework.data.domain.Page;

public interface ProductService {

    /**
     * 根据条件查询商品信息
     *
     * @param productVo
     * @return
     */
    Page<ProductPo> findProductList(ProductVo productVo);
}
