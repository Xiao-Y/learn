package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.WhiteListBuildParam;
import com.billow.system.pojo.vo.WhiteListVo;
import com.billow.system.pojo.search.WhiteListSearchParam;
import com.billow.system.pojo.po.WhiteListPo;
import com.billow.system.service.WhiteListService;
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
@Api(tags = {"WhiteListApi"},value = "")
@RestController
@RequestMapping("/whiteListApi")
public class WhiteListApi extends HighLevelApi<WhiteListService, WhiteListPo, WhiteListVo, WhiteListBuildParam, WhiteListSearchParam> {

}
