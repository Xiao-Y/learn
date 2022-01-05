package com.billow.system.app;

import cn.hutool.core.lang.tree.Tree;
import com.billow.common.base.BaseApi;
import com.billow.common.utils.UserTools;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuPermissionService;
import com.billow.system.service.MenuService;
import com.billow.system.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 登陆时，获取角色菜单和菜单路由,不受权限控制
 *
 * @author liuyongtao
 * @create 2018-05-26 9:27
 */
@Slf4j
@RestController
@RequestMapping("/loginApp")
@Api(value = "登陆时，获取角色菜单和菜单路由,不受权限控制")
public class LoginApp extends BaseApi
{

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuPermissionService menuPermissionService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private UserTools userTools;

//    /**
//     * 登陆后查询的菜单
//     *
//     * @param
//     * @return HomeEx
//     * @author 千面
//     * @date 2021/12/30 13:37
//     */
//    @GetMapping("/homeMenus")
//    @ApiOperation(value = "初始化菜单信息", notes = "初始化菜单信息")
//    public HomeEx homeMenus()
//    {
//        MenuVo ex = new MenuVo();
//        // 获取角色信息
//        List<RoleVo> roleVos = this.getCurrentRoleVos();
//        ex.setUserCode(userTools.getCurrentUserCode());
//        ex.setRoleVos(roleVos);
//        ex.setValidInd(true);
//        // 构建角色的菜单
//        List<MenuEx> menuExes = menuService.homeMenus(ex);
//        HomeEx homeEx = new HomeEx();
//        homeEx.setMenus(menuExes);
//        return homeEx;
//    }

    /**
     * 登陆后查询的路由列表
     *
     * @param
     * @return List<MenuEx>
     * @author 千面
     * @date 2021/12/30 13:37
     */
    @GetMapping("/findRouterList")
    @ApiOperation(value = "查询角色的的路由列表", notes = "查询角色的的路由列表")
    public List<MenuPo> findRouterList()
    {
        // 获取角色信息
        List<RoleVo> roleVos = this.getCurrentRoleVos();
        // 查询角色的的路由列表
        return menuService.findRouterList(roleVos);
    }

    @GetMapping("/findPermissionByMenuId/{menuId}")
    @ApiOperation(value = "查询菜单下的资源信息", notes = "查询菜单下的资源信息")
    public List<PermissionVo> findPermissionByMenuId(@PathVariable("menuId") Long menuId)
    {
        return menuPermissionService.findPermissionByMenuId(menuId);
    }


    @GetMapping("/findHomeMenu")
    @ApiOperation(value = "查询登陆时菜单信息", notes = "查询登陆时菜单信息")
    public List<Tree<Long>> findHomeMenu()
    {
        List<String> currentRoleCode = userTools.getCurrentRoleCode();
//        return roleMenuService.findMenuByRoleCode(Arrays.asList("custom"), true);
        return menuService.findMenuByRoleCode(currentRoleCode, true);
    }

    /**
     * 获取角色信息
     *
     * @param
     * @return List<RoleVo>
     * @author 千面
     * @date 2021/12/30 13:41
     */
    private List<RoleVo> getCurrentRoleVos()
    {
        return userTools.getCurrentRoleCode()
                .stream()
                .map(m -> {
                    RoleVo roleVo = new RoleVo();
                    roleVo.setRoleCode(m);
                    return roleVo;
                })
                .collect(Collectors.toList());
    }
}
