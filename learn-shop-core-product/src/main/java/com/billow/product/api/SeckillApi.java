package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.SeckillBuildParam;
import com.billow.product.pojo.po.SeckillPo;
import com.billow.product.pojo.search.SeckillSearchParam;
import com.billow.product.pojo.vo.SeckillVo;
import com.billow.product.service.SeckillService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 * @version v2.0
 */
@Slf4j
@Api(tags = {"SeckillApi"},value = "限时购表")
@RestController
@RequestMapping("/seckillApi")
public class SeckillApi extends HighLevelApi<SeckillService, SeckillPo, SeckillVo, SeckillBuildParam, SeckillSearchParam>
{

}
