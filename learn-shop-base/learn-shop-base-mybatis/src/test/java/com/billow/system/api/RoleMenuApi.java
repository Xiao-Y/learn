package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.RoleMenuBuildParam;
import com.billow.system.pojo.vo.RoleMenuVo;
import com.billow.system.pojo.search.RoleMenuSearchParam;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.service.RoleMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "RoleMenuApi",description =  "")
@RestController
@RequestMapping("/roleMenuApi")
public class RoleMenuApi extends HighLevelApi<RoleMenuService, RoleMenuPo, RoleMenuVo, RoleMenuBuildParam, RoleMenuSearchParam> {

}
