package com.billow.dao;

import com.billow.common.utils.QueryUtils;

import com.billow.pojo.po.ProductPo;
import com.billow.pojo.vo.ProductVo;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpec {

    public static Specification<ProductPo> byProductList(ProductVo vo) {

        return (Root<ProductPo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {

            ProductPo po = ConvertUtils.convert(vo, ProductPo.class);
            List<Predicate> all = new ArrayList();
            // 添加 like 查询
            QueryUtils.getPredicateLike(all, root, cb, po, "commodityName");
            QueryUtils.getPredicateLike(all, root, cb, po, "localityGrowth");
            // 添加 equal 查询
            QueryUtils.getPredicates(all, root, cb, po);

            Predicate[] predicates = ConvertUtils.toArray(all, Predicate.class);
            return cb.and(predicates);
        };
    }
}
