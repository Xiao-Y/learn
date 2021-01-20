package com.billow.jpa;

import com.billow.jpa.utils.QueryUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 默认的查询条件，属性不为空时拼接
 */
public class DefaultSpec<T> implements Specification<T> {

    private T t;

    public DefaultSpec(T t) {
        this.t = t;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = QueryUtils.getPredicateEqual(root, criteriaBuilder, t);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        return criteriaBuilder.and(predicates);
    }
}
