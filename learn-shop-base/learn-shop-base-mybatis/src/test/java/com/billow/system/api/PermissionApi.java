package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.PermissionBuildParam;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.pojo.search.PermissionSearchParam;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.service.PermissionService;
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
@Api(tags = {"PermissionApi"},value = "")
@RestController
@RequestMapping("/permissionApi")
public class PermissionApi extends HighLevelApi<PermissionService, PermissionPo, PermissionVo, PermissionBuildParam, PermissionSearchParam> {

}
