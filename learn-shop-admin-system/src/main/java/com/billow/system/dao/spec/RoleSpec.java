package com.billow.system.dao.spec;

import com.billow.jpa.utils.QueryUtils;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色查询条件
 *
 * @author liuyongtao
 * @create 2019-08-22 15:48
 */
public class RoleSpec implements Specification<RolePo> {

    private RolePo rolePo;

    public RoleSpec(RoleVo roleVo) {
        this.rolePo = ConvertUtils.convert(roleVo, RolePo.class);
    }


    @Override
    public Predicate toPredicate(Root<RolePo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        RolePo convert = ConvertUtils.convert(rolePo, RolePo.class);
        List<Predicate> all = new ArrayList<>();

        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "roleName");
        QueryUtils.getPredicateEqual(all, root, criteriaBuilder, convert, "roleCode");

        return criteriaBuilder.and(QueryUtils.converListToArray(all));
    }
}
