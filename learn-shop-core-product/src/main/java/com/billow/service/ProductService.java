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

    /**
     * 保存商品信息
     *
     * @param productVo
     * @param userCode
     */
    void saveProduct(ProductVo productVo, String userCode) throws Exception;

    /**
     * 更新商品信息
     *
     * @param productVo
     * @param userCode
     * @throws Exception
     */
    void updateProduct(ProductVo productVo, String userCode) throws Exception;

    /**
     * 根据id删除商品信息
     *
     * @param id
     * @param userCode
     */
    ProductVo deleteProductById(String id, String userCode) throws Exception;

}
