package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.MenuBuildParam;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.search.MenuSearchParam;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.service.MenuService;
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
@Api(tags = {"MenuApi"},value = "")
@RestController
@RequestMapping("/menuApi")
public class MenuApi extends HighLevelApi<MenuService, MenuPo, MenuVo, MenuBuildParam, MenuSearchParam> {

}
