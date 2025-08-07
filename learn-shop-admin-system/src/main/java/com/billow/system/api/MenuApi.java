package com.billow.system.api;

import cn.hutool.core.lang.tree.Tree;
import com.billow.aop.annotation.OperationLog;
import com.billow.common.base.BaseApi;
import com.billow.common.utils.UserTools;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @OperationLog
    @GetMapping("/findMenus")
    @ApiOperation(value = "菜单管理信息", notes = "菜单管理信息")
    public List<Tree<Long>> findMenus() {
        return menuService.findMenus();
    }

    @GetMapping("/findMenuById/{id}")
    @ApiOperation(value = "根据id查询菜单信息", notes = "根据id查询菜单信息")
    public MenuVo findMenuById(@PathVariable("id") Long id) {
        return menuService.findMenuById(id);
    }

    @PutMapping("/saveOrUpdateMenu")
    @ApiOperation(value = "修改、添加菜单信息", notes = "修改、添加菜单信息")
    public MenuVo saveOrUpdateMenu(@RequestBody MenuVo menuVo) throws Exception {
        return menuService.saveOrUpdateMenu(menuVo);
    }

    @DeleteMapping("/delMenuByIds")
    @ApiOperation(value = "删除菜单信息", notes = "删除菜单信息")
    public MenuVo delMenuByIds(@RequestBody MenuVo menuVo) {
        //防止重复id
        Set<String> ids = menuVo.getIds();
        menuService.delMenuByIds(ids);
        return menuVo;
    }

    @ApiOperation(value = "查询 menuCode 的个数")
    @GetMapping("/checkMenuCode/{menuCode}")
    public Long checkMenuCode(@PathVariable("menuCode") String menuCode) {
        return menuService.countMenuCodeByMenuCode(menuCode);
    }

    @ApiOperation(value = "根据权限ID查询出绑定的菜单信息")
    @GetMapping("/findMenuByPermissionId/{permissionId}")
    public List<MenuVo> findMenuByPermissionId(@PathVariable("permissionId") Long permissionId) {
        return menuService.findMenuByPermissionId(permissionId);
    }
}
