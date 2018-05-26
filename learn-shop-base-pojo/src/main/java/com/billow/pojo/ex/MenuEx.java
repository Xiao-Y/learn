package com.billow.pojo.ex;

import com.billow.pojo.po.sys.PermissionPo;

import java.util.List;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-26 9:30
 */
public class MenuEx extends PermissionPo {

    /**
     * 子级菜单
     */
    private List<MenuEx> cMenus;

    public List<MenuEx> getcMenus() {
        return cMenus;
    }

    public MenuEx setcMenus(List<MenuEx> cMenus) {
        this.cMenus = cMenus;
        return this;
    }
}
