package com.billow.service;

import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductImageVo;
import com.billow.pojo.vo.product.ProductVo;
import org.springframework.data.domain.Page;

import java.util.List;

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

    /**
     * 上传商品图片，保存图片信息
     *
     * @param productImageVo
     * @param userCode
     */
    void uploadProductImage(ProductImageVo productImageVo, String userCode) throws Exception;

    /**
     * 通过商品id查询出商品图片
     *
     * @param productId      商品id
     * @param productImageVo 查询条件
     * @return
     * @throws Exception
     */
    List<ProductImageVo> findProductImageByProductId(String productId, ProductImageVo productImageVo) throws Exception;
}
