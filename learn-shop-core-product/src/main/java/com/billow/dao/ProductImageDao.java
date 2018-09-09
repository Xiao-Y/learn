package com.billow.dao;

import com.billow.pojo.po.product.ProductImagePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductImageDao extends JpaRepository<ProductImagePo, String>, JpaSpecificationExecutor<ProductImagePo> {
}
