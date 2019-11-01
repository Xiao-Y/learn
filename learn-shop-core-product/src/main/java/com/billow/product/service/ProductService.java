package com.billow.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductImageVo;
import com.billow.product.pojo.vo.ProductVo;

import java.util.List;

public interface ProductService {

    /**
     * 根据条件查询商品信息
     *
     * @param productVo
     * @return
     */
    IPage<ProductPo> findProductList(ProductVo productVo);

    /**
     * 保存商品信息
     *
     * @param productVo
     */
    void saveProduct(ProductVo productVo) throws Exception;

    /**
     * 更新商品信息
     *
     * @param productVo
     * @throws Exception
     */
    void updateProduct(ProductVo productVo) throws Exception;

    /**
     * 根据id删除商品信息
     *
     * @param id
     */
    ProductVo deleteProductById(String id) throws Exception;

    /**
     * 上传商品图片，保存图片信息
     *
     * @param productImageVo
     */
    void uploadProductImage(ProductImageVo productImageVo) throws Exception;

    /**
     * 通过商品id查询出商品图片
     *
     * @param productId      商品id
     * @param productImageVo 查询条件
     * @return
     * @throws Exception
     */
    List<ProductImageVo> findProductImageByProductId(String productId, ProductImageVo productImageVo) throws Exception;

    /**
     * 通过图片id删除商品图片
     *
     * @param id 图片id
     */
    void deleteProductImageById(String id) throws Exception;
}
