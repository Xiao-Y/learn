package com.billow.system.dao;

import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.query.SelectRoleQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleDao extends JpaRepository<RolePo, Long>, JpaSpecificationExecutor<RolePo> {

    @Query("select id as id,r.roleCode as roleCode,r.roleName as roleName from RolePo r")
    List<SelectRoleQuery> findSelectRole();

    /**
     * 批量查询角色信息
     *
     * @param ids
     * @return java.util.List<com.billow.system.pojo.vo.RoleVo>
     * @author LiuYongTao
     * @date 2019/7/31 13:52
     */
    List<RolePo> findByIdIn(List<Long> ids);

    /**
     * 查询 roleCode 的个数
     *
     * @param roleCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/8/7 10:27
     */
    Integer countRoleCodeByRoleCode(String roleCode);
}
