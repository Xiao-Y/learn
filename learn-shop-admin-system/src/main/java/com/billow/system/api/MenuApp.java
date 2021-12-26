package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.common.utils.UserTools;
import com.billow.system.pojo.ex.HomeEx;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.system.service.redis.RoleMenuRedisKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("/menuApp")
@Api(value = "登陆时，获取角色菜单和菜单路由,不受权限控制")
public class MenuApp extends BaseApi {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserTools userTools;

    @GetMapping("/homeMenus")
    @ApiOperation(value = "初始化菜单信息", notes = "初始化菜单信息")
    public HomeEx homeMenus() {
        MenuVo ex = new MenuVo();
        // 获取角色信息
        List<RoleVo> roleVos = userTools.getCurrentRoleCode()
                .stream()
                .map(m -> {
                    RoleVo roleVo = new RoleVo();
                    roleVo.setRoleCode(m);
                    return roleVo;
                })
                .collect(Collectors.toList());
        ex.setUserCode(userTools.getCurrentUserCode());
        ex.setRoleVos(roleVos);
        ex.setValidInd(true);

        List<MenuEx> menuExes = menuService.homeMenus(ex);

        HomeEx homeEx = new HomeEx();
        homeEx.setMenus(menuExes);
        return homeEx;
    }

    @GetMapping("/findRouterList")
    @ApiOperation(value = "查询角色的的路由列表", notes = "查询角色的的路由列表")
    public List<MenuEx> findRouterList() {
        // 获取角色信息
        List<RoleVo> roleVos = userTools.getCurrentRoleCode()
                .stream()
                .map(m -> {
                    RoleVo roleVo = new RoleVo();
                    roleVo.setRoleCode(m);
                    return roleVo;
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleVos)) {
            return new ArrayList<>();
        }
        // 查询角色的的路由列表
        return menuService.findRouterList(roleVos);
    }
}
