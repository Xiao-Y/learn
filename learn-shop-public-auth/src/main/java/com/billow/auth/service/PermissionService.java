package com.billow.auth.service;

import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePo;
import com.billow.auth.pojo.vo.PermissionVo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface PermissionService {

    /**
     * 判断用户是否有权限
     *
     * @param [request, authentication]
     * @return boolean
     * @author LiuYongTao
     * @date 2019/5/23 20:07
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

    /**
     * 通过角色令牌,查询角色的权限集合
     *
     * @param rolePo
     * @return java.util.List<com.billow.auth.pojo.po.PermissionPo>
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
    Page<PermissionPo> findPermissionList(PermissionVo permissionVo);
}
