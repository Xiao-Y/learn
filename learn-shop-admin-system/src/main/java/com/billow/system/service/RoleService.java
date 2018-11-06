package com.billow.system.service;

import com.billow.system.pojo.vo.RoleVo;

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
}
