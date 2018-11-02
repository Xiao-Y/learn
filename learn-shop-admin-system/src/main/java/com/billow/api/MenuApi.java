package com.billow.api;

import com.billow.common.base.BaseApi;
import com.billow.common.business.ex.RoleEx;
import com.billow.common.enums.RdsKeyEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.ex.HomeEx;

import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.vo.PermissionVo;
import com.billow.pojo.vo.RoleVo;
import com.billow.service.MenuService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/**
 * 菜单管理
 *
 * @author liuyongtao
 * @create 2018-05-26 9:27
 */
@RestController
@RequestMapping("/menuApi")
@Api(value = "MenuApi", description = "菜单管理")
public class MenuApi extends BaseApi {

    @Autowired
    private MenuService menuService;

    @GetMapping("/homeMenus")
    @ApiOperation(value = "初始化菜单信息", notes = "初始化菜单信息")
    public BaseResponse<HomeEx> homeMenus() {
        BaseResponse<HomeEx> baseResponse = this.getBaseResponse();
        try {
            PermissionVo ex = new PermissionVo();
            String userCode = super.findUserCode();
            List<RoleEx> roleExs = super.findRoleVos();
            List<RoleVo> roleVos = ConvertUtils.convert(roleExs, RoleVo.class);
            ex.setUserCode(userCode).setRoleVos(roleVos).setValidInd(true);
            List<MenuEx> menuExes = menuService.homeMenus(ex);

            HomeEx homeEx = new HomeEx();
            homeEx.setMenus(menuExes);
            baseResponse.setResData(homeEx);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @GetMapping("/findMenus")
    @ApiOperation(value = "菜单管理信息", notes = "菜单管理信息")
    public BaseResponse<List<MenuEx>> findMenus() {
        BaseResponse<List<MenuEx>> baseResponse = this.getBaseResponse();
        try {
            List<MenuEx> menuExes = super.getRedisValues(RdsKeyEnum.FIND_MENUS.getKey(), MenuEx.class);
            if (ToolsUtils.isEmpty(menuExes)) {
                menuExes = menuService.findMenus();
                super.setRedisObject(RdsKeyEnum.FIND_MENUS.getKey(), menuExes);
            }
            baseResponse.setResData(menuExes);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @GetMapping("/findMenuById/{id}")
    @ApiOperation(value = "根据id查询菜单信息", notes = "根据id查询菜单信息")
    public BaseResponse<PermissionVo> findMenuById(@PathVariable("id") Long id) {
        BaseResponse<PermissionVo> baseResponse = this.getBaseResponse();
        try {
            StringBuilder key = new StringBuilder(RdsKeyEnum.FIND_MENU_BY_ID.getKey());
            key.append(":");
            key.append(id.toString());
            PermissionVo ex = super.getRedisValue(key.toString(), PermissionVo.class);
            if (ex == null) {
                ex = menuService.findMenuById(id);
                super.setRedisObject(key.toString(), ex);
            }
            ex.setValidInd(null);
            baseResponse.setResData(ex);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @PutMapping("/saveOrUpdateMenu")
    @ApiOperation(value = "修改、添加菜单信息", notes = "修改、添加菜单信息")
    public BaseResponse<PermissionVo> saveOrUpdateMenu(@RequestBody PermissionVo permissionVo) {
        BaseResponse<PermissionVo> baseResponse = this.getBaseResponse();
        try {
            permissionVo.setUpdaterCode(this.findUserCode());
            PermissionVo vo = menuService.saveOrUpdateMenu(permissionVo);
            baseResponse.setResData(vo);

            try {
                redisTemplate.delete(RdsKeyEnum.FIND_MENUS.getKey());
                StringBuilder key = new StringBuilder(RdsKeyEnum.FIND_MENU_BY_ID.getKey());
                key.append(":");
                key.append(vo.getId().toString());
                super.setRedisObject(key.toString(), vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @DeleteMapping("/delMenuByIds")
    @ApiOperation(value = "修改、添加菜单信息", notes = "修改、添加菜单信息")
    public BaseResponse<PermissionVo> delMenuByIds(@RequestBody PermissionVo permissionVo) {
        BaseResponse<PermissionVo> baseResponse = this.getBaseResponse();
        try {
            //防止重复id
            Set<String> ids = permissionVo.getIds();
            System.out.println(ids);
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
                e.printStackTrace();
            }
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }
}
