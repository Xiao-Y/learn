package com.billow.service;

import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.vo.sys.PermissionVo;

import java.util.List;

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
     * @param permissionVo
     * @return java.util.List<com.billow.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2018/5/29 20:10
     */
    List<MenuEx> findMenus(PermissionVo permissionVo);
}
