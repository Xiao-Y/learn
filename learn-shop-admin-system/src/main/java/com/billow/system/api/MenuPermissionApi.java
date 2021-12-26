package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.MenuPermissionBuildParam;
import com.billow.system.pojo.vo.MenuPermissionVo;
import com.billow.system.pojo.search.MenuPermissionSearchParam;
import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.system.service.MenuPermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单权限 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-26
 */
@Slf4j
@Api(tags = {"MenuPermissionApi"}, value = "菜单权限")
@RestController
@RequestMapping("/menuPermissionApi")
public class MenuPermissionApi extends HighLevelApi<MenuPermissionService, MenuPermissionPo, MenuPermissionVo,
        MenuPermissionBuildParam, MenuPermissionSearchParam> {

}
