package com.billow.dao;

import com.billow.pojo.po.product.ProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<ProductPo, String>,JpaSpecificationExecutor<ProductPo> {
}
