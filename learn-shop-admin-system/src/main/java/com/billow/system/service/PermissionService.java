package com.billow.system.service;

import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.PermissionVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    /**
     * 通过角色令牌,查询角色的权限集合
     *
     * @param rolePo
     * @return java.util.List<com.billow.auth.pojo.vo.PermissionVo>
     * @author LiuYongTao
     * @date 2019/5/23 20:07
     */
    Set<PermissionPo> findPermissionByRole(RolePo rolePo);

    /**
     * 根据条件查询权限列表
     *
     * @param permissionVo
     * @return org.springframework.data.domain.Page<com.billow.auth.pojo.po.PermissionPo>
     * @author LiuYongTao
     * @date 2019/7/9 15:59
     */
    Page<PermissionVo> findPermissionList(PermissionVo permissionVo);

    /**
     * 根据ID删除权限
     *
     * @param id
     * @return com.billow.system.pojo.vo.PermissionVo
     * @author LiuYongTao
     * @date 2019/7/10 16:12
     */
    PermissionVo deletePermissionById(Long id);

    /**
     * 添加权限信息
     *
     * @param permissionVo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/10 18:01
     */
    void savePermission(PermissionVo permissionVo);

    /**
     * 更新权限信息
     *
     * @param permissionVo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/10 18:01
     */
    void updatePermission(PermissionVo permissionVo);

    /**
     * 根据ID禁用权限
     *
     * @param id
     * @return com.billow.system.pojo.vo.PermissionVo
     * @author LiuYongTao
     * @date 2019/7/11 17:12
     */
    PermissionVo prohibitPermissionById(Long id);

    /**
     * 查询权限列表
     *
     * @return java.util.List<com.billow.system.pojo.vo.PermissionVo>
     * @author LiuYongTao
     * @date 2019/7/11 19:40
     */
    List<PermissionVo> findPermissionAll();
}
