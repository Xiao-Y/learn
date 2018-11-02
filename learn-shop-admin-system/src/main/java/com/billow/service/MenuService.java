package com.billow.service;

import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.vo.PermissionVo;

import java.util.List;
import java.util.Set;

public interface MenuService {

    /**
     * 查询菜单
     *
     * @return java.util.List<com.billow.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2018/5/26 10:01
     */
    List<MenuEx> homeMenus(PermissionVo permissionVo);

    /**
     * 菜单管理信息
     *
     * @return java.util.List<com.billow.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2018/5/29 20:10
     */
    List<MenuEx> findMenus();

    /**
     * 根据id查询菜单信息
     *
     * @param id
     * @return
     */
    PermissionVo findMenuById(Long id);

    /**
     * 修改、添加菜单信息
     *
     * @param permissionVo
     */
    PermissionVo saveOrUpdateMenu(PermissionVo permissionVo) throws Exception;

    /**
     * 通过ids 删除菜单信息
     *
     * @param ids
     */
    void delMenuByIds(Set<String> ids);
}
