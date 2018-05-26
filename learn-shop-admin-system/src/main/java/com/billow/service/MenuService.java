package com.billow.service;

import com.billow.pojo.ex.MenuEx;

import java.util.List;

public interface MenuService {

    /**
     * 查询菜单
     *
     * @return java.util.List<com.billow.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2018/5/26 10:01
     */
    List<MenuEx> indexMenus();
}
