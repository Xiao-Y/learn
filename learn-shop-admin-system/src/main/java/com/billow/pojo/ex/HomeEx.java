package com.billow.pojo.ex;


import java.util.ArrayList;
import java.util.List;

/**
 * 登陆用户的信息
 *
 * @author liuyongtao
 * @create 2018-05-29 17:47
 */
public class HomeEx {

    List<String> roleCodes = new ArrayList<>();
    List<MenuEx> menus = new ArrayList<>();

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public HomeEx setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
        return this;
    }

    public List<MenuEx> getMenus() {
        return menus;
    }

    public HomeEx setMenus(List<MenuEx> menus) {
        this.menus = menus;
        return this;
    }
}
