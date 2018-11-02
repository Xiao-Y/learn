package com.billow.dao;

import com.billow.pojo.po.ProductImagePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductImageDao extends JpaRepository<ProductImagePo, String>, JpaSpecificationExecutor<ProductImagePo> {

    /**
     * 通过商品id和有效标志查询商品
     *
     * @param productId 商品id
     * @param validInd  有效标志
     * @return
     */
    List<ProductImagePo> findByProductIdAndValidInd(String productId, boolean validInd);
}
