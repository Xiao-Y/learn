package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.RolePermissionBuildParam;
import com.billow.system.pojo.vo.RolePermissionVo;
import com.billow.system.pojo.search.RolePermissionSearchParam;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.service.RolePermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2022-01-04
 * @version v2.0
 */
@Slf4j
@Api(tags = {"RolePermissionApi"},value = "")
@RestController
@RequestMapping("/rolePermissionApi")
public class RolePermissionApi extends HighLevelApi<RolePermissionService, RolePermissionPo, RolePermissionVo, RolePermissionBuildParam, RolePermissionSearchParam> {

}
