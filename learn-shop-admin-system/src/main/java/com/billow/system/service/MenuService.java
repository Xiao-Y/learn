package com.billow.system.service;

import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.MenuVo;

import java.util.List;
import java.util.Set;

public interface MenuService {

    /**
     * 查询菜单
     *
     * @return java.util.List<MenuEx>
     * @author LiuYongTao
     * @date 2018/5/26 10:01
     */
    List<MenuEx> homeMenus(MenuVo menuVo);

    /**
     * 菜单管理信息
     *
     * @return java.util.List<MenuEx>
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
    MenuVo findMenuById(Long id);

    /**
     * 修改、添加菜单信息
     *
     * @param menuVo
     */
    MenuVo saveOrUpdateMenu(MenuVo menuVo) throws Exception;

    /**
     * 通过ids 删除菜单信息
     *
     * @param ids
     */
    void delMenuByIds(Set<String> ids);

    /**
     * 根据角色查询出菜单信息
     *
     * @param rolePo
     * @return java.util.Set<com.billow.system.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2019/7/22 17:55
     */
    Set<MenuEx> findMenuByRole(RolePo rolePo);

    /**
     * 查询 menuCode 的个数
     *
     * @param menuCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/7/24 14:10
     */
    Integer countMenuCodeByMenuCode(String menuCode);

    /**
     * 查询出指定节点的所有直接父级菜单id
     *
     * @param id
     * @return java.util.Set<java.lang.String>
     * @author LiuYongTao
     * @date 2019/8/2 10:52
     */
    Set<String> getParentByCurrentId(Long id);
}
