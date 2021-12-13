package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.SeckillSessionBuildParam;
import com.billow.product.pojo.po.SeckillSessionPo;
import com.billow.product.pojo.search.SeckillSessionSearchParam;
import com.billow.product.pojo.vo.SeckillSessionVo;
import com.billow.product.service.SeckillSessionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 * @version v2.0
 */
@Slf4j
@Api(tags = {"SeckillSessionApi"},value = "限时购场次表")
@RestController
@RequestMapping("/seckillSessionApi")
public class SeckillSessionApi extends HighLevelApi<SeckillSessionService, SeckillSessionPo, SeckillSessionVo, SeckillSessionBuildParam, SeckillSessionSearchParam> {

}
