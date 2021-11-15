package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSafeguardBuildParam;
import com.billow.product.pojo.po.GoodsSafeguardPo;
import com.billow.product.pojo.search.GoodsSafeguardSearchParam;
import com.billow.product.pojo.vo.GoodsSafeguardVo;
import com.billow.product.service.GoodsSafeguardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 增值保障 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsSafeguardApi"},value = "增值保障")
@RestController
@RequestMapping("/goodsSafeguardApi")
public class GoodsSafeguardApi extends HighLevelApi<GoodsSafeguardService, GoodsSafeguardPo, GoodsSafeguardVo, GoodsSafeguardBuildParam, GoodsSafeguardSearchParam>
{

}
