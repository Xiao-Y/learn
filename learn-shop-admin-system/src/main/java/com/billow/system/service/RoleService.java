package com.billow.system.service;

import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    /**
     * 通过用户id 查询用户角色信息
     *
     * @param userId 用户id
     * @return java.util.List<RoleVo>
     * @author LiuYongTao
     * @date 2018/11/5 16:19
     */
    List<RoleVo> findRoleListInfoByUserId(Long userId);

    /**
     * 根据条件查询角色列表信息
     *
     * @param roleVo
     * @return
     */
    Page<RolePo> findRoleByCondition(RoleVo roleVo) throws Exception;
}
