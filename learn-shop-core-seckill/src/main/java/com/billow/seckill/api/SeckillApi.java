package com.billow.seckill.api;

import com.billow.seckill.common.pojo.build.SeckillBuildParam;
import com.billow.seckill.common.pojo.po.SeckillPo;
import com.billow.seckill.common.pojo.search.SeckillSearchParam;
import com.billow.seckill.common.pojo.vo.SeckillVo;
import com.billow.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀库存表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Slf4j
@Api(tags = {"SeckillApi"}, value = "秒杀库存表")
@RestController
@RequestMapping("/seckillApi")
public class SeckillApi extends HighLevelApi<SeckillService, SeckillPo, SeckillVo, SeckillBuildParam, SeckillSearchParam> {

}
