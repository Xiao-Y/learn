package com.billow.api;

import com.billow.common.base.BaseApi;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.ex.HomeEx;
import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.vo.sys.PermissionVo;
import com.billow.pojo.vo.sys.RoleVo;
import com.billow.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            List<RoleVo> roleVos = super.findRoleVos();
            ex.setUserCode(userCode).setRoleVos(roleVos).setValidInd(true);
            List<MenuEx> menuExes = menuService.homeMenus(ex);

            HomeEx homeEx = new HomeEx();
            homeEx.setMenus(menuExes);
            baseResponse.setResData(homeEx);
        } catch (Exception e) {
            baseResponse.setResCode(ResCodeEnum.FAIL);
            this.getErrorInfo(e);
        }
        return baseResponse;
    }

    @GetMapping("/findMenus")
    @ApiOperation(value = "菜单管理信息", notes = "菜单管理信息")
    public BaseResponse<List<MenuEx>> findMenus() {
        BaseResponse<List<MenuEx>> baseResponse = this.getBaseResponse();
        try {
            List<MenuEx> menuExes = menuService.findMenus();
            baseResponse.setResData(menuExes);
        } catch (Exception e) {
            baseResponse.setResCode(ResCodeEnum.FAIL);
            this.getErrorInfo(e);
        }
        return baseResponse;
    }

    @GetMapping("/findMenuById/{id}")
    @ApiOperation(value = "根据id查询菜单信息", notes = "根据id查询菜单信息")
    public BaseResponse<PermissionVo> findMenuById(@PathVariable("id") Long id) {
        BaseResponse<PermissionVo> baseResponse = this.getBaseResponse();
        try {
            PermissionVo ex = menuService.findMenuById(id);
            ex.setValidInd(null);
            baseResponse.setResData(ex);
        } catch (Exception e) {
            baseResponse.setResCode(ResCodeEnum.FAIL);
            this.getErrorInfo(e);
        }
        return baseResponse;
    }
}
