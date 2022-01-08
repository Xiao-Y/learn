package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.UserRoleBuildParam;
import com.billow.system.pojo.vo.UserRoleVo;
import com.billow.system.pojo.search.UserRoleSearchParam;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.service.UserRoleService;
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
@Api(tags = {"UserRoleApi"},value = "")
@RestController
@RequestMapping("/userRoleApi")
public class UserRoleApi extends HighLevelApi<UserRoleService, UserRolePo, UserRoleVo, UserRoleBuildParam, UserRoleSearchParam> {

}
