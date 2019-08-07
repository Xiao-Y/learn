package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.tools.utlis.UserTools;
import com.billow.system.pojo.ex.HomeEx;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.tools.enums.RdsKeyEnum;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author liuyongtao
 * @create 2018-05-26 9:27
 */
@Slf4j
@RestController
@RequestMapping("/menuApi")
@Api(value = "菜单管理")
public class MenuApi extends BaseApi {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserTools userTools;

    @GetMapping("/homeMenus")
    @ApiOperation(value = "初始化菜单信息", notes = "初始化菜单信息")
    public HomeEx homeMenus() {
        MenuVo ex = new MenuVo();
        // 获取角色信息
        List<RoleVo> roleVos = userTools.getCurrentRoleCode().stream().map(m -> {
            RoleVo roleVo = new RoleVo();
            roleVo.setRoleCode(m);
            return roleVo;
        }).collect(Collectors.toList());
        ex.setUserCode(userTools.getCurrentUserCode());
        ex.setRoleVos(roleVos);
        ex.setValidInd(true);

        List<MenuEx> menuExes = menuService.homeMenus(ex);

        HomeEx homeEx = new HomeEx();
        homeEx.setMenus(menuExes);
        return homeEx;
    }

    @GetMapping("/findMenus")
    @ApiOperation(value = "菜单管理信息", notes = "菜单管理信息")
    public List<MenuEx> findMenus() {
        List<MenuEx> menuExes = super.getRedisValues(RdsKeyEnum.FIND_MENUS, MenuEx.class);
        if (ToolsUtils.isEmpty(menuExes)) {
            menuExes = menuService.findMenus();
            super.setRedisObject(RdsKeyEnum.FIND_MENUS.getKey(), menuExes);
        }
        return menuExes;
    }

    @GetMapping("/findMenuById/{id}")
    @ApiOperation(value = "根据id查询菜单信息", notes = "根据id查询菜单信息")
    public MenuVo findMenuById(@PathVariable("id") Long id) {
        StringBuilder key = new StringBuilder(RdsKeyEnum.FIND_MENU_BY_ID.getKey());
        key.append(":");
        key.append(id.toString());
        MenuVo ex = super.getRedisValue(key.toString(), MenuVo.class);
        if (ex == null) {
            ex = menuService.findMenuById(id);
            super.setRedisObject(key.toString(), ex);
        }
        ex.setValidInd(null);
        return ex;
    }

    @PutMapping("/saveOrUpdateMenu")
    @ApiOperation(value = "修改、添加菜单信息", notes = "修改、添加菜单信息")
    public MenuVo saveOrUpdateMenu(@RequestBody MenuVo menuVo) throws Exception {
        MenuVo vo = menuService.saveOrUpdateMenu(menuVo);

        try {
            redisTemplate.delete(RdsKeyEnum.FIND_MENUS.getKey());
            StringBuilder key = new StringBuilder(RdsKeyEnum.FIND_MENU_BY_ID.getKey());
            key.append(":");
            key.append(vo.getId().toString());
            super.setRedisObject(key.toString(), vo);
        } catch (Exception e) {
            log.error("redis 异常：", e);
        }
        return vo;
    }

    @DeleteMapping("/delMenuByIds")
    @ApiOperation(value = "删除菜单信息", notes = "删除菜单信息")
    public MenuVo delMenuByIds(@RequestBody MenuVo menuVo) {
        //防止重复id
        Set<String> ids = menuVo.getIds();
        menuService.delMenuByIds(ids);
        try {
            redisTemplate.delete(RdsKeyEnum.FIND_MENUS.getKey());
            for (String id : ids) {
                StringBuilder key = new StringBuilder(RdsKeyEnum.FIND_MENU_BY_ID.getKey());
                key.append(":");
                key.append(id);
                redisTemplate.delete(key.toString());
            }
        } catch (Exception e) {
            log.error("redis 异常：", e);
        }
        return menuVo;
    }

    @ApiOperation(value = "查询 menuCode 的个数")
    @GetMapping("/checkMenuCode/{menuCode}")
    public Integer checkMenuCode(@PathVariable("menuCode") String menuCode) {
        return menuService.countMenuCodeByMenuCode(menuCode);
    }
}
