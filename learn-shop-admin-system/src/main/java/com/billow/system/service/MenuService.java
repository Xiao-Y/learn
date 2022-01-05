package com.billow.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;

import java.util.List;
import java.util.Set;

public interface MenuService extends IService<MenuPo> {
//
//    /**
//     * 查询菜单
//     *
//     * @return java.util.List<MenuEx>
//     * @author LiuYongTao
//     * @date 2018/5/26 10:01
//     */
//    List<MenuEx> homeMenus(MenuVo menuVo);

    /**
     * 菜单管理信息
     *
     * @return java.util.List<MenuEx>
     * @author LiuYongTao
     * @date 2018/5/29 20:10
     */
    List<Tree<Long>> findMenus();

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
     * @return java.util.Set<com.billow.system.pojo.MenuPo>
     * @author LiuYongTao
     * @date 2019/7/22 17:55
     */
    List<MenuPo> findMenuByRole(RolePo rolePo);

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

    /**
     * 查询角色的的路由列表
     *
     * @param roleVos
     * @return {@link List< MenuPo>}
     * @author xiaoy
     * @since 2021/12/25 21:37
     */
    List<MenuPo> findRouterList(List<RoleVo> roleVos);

    /**
     * 查询出指定角色下的菜单树信息
     *
     * @param roleCodes     可以为空
     * @param isDisplay 是否只查询显示的菜单
     * @return List<Tree < Long>>
     * @author 千面
     * @date 2022/1/4 9:47
     */
    List<Tree<Long>> findMenuByRoleCode(List<String> roleCodes, boolean isDisplay);
}
