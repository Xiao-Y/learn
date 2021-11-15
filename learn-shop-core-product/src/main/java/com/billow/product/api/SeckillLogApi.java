package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.SeckillLogBuildParam;
import com.billow.product.pojo.po.SeckillLogPo;
import com.billow.product.pojo.search.SeckillLogSearchParam;
import com.billow.product.pojo.vo.SeckillLogVo;
import com.billow.product.service.SeckillLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 * @version v2.0
 */
@Slf4j
@Api(tags = {"SeckillLogApi"},value = "限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。")
@RestController
@RequestMapping("/seckillLogApi")
public class SeckillLogApi extends HighLevelApi<SeckillLogService, SeckillLogPo, SeckillLogVo, SeckillLogBuildParam, SeckillLogSearchParam>
{

}
