package com.billow.system.dao.spec;

import com.billow.jpa.utils.QueryUtils;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 *
 * @author liuyongtao
 * @create 2019-08-22 14:07
 */
public class PermissionSpec implements Specification<PermissionPo> {

    private PermissionPo permissionPo;

    public PermissionSpec(PermissionVo permissionVo) {
        this.permissionPo = ConvertUtils.convert(permissionVo, PermissionPo.class);
    }

    @Override
    public Predicate toPredicate(Root<PermissionPo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        // 分页查询时，不会再次 new PermissionSpec，条件经过 getPredicateALike 后被置空，count 时异常
        PermissionPo convert = ConvertUtils.convert(permissionPo, PermissionPo.class);
        List<Predicate> all = new ArrayList<>();

        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "permissionName");
        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "permissionCode");
        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "url");
        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "systemModule");

        return criteriaBuilder.and(QueryUtils.converListToArray(all));
    }
}
