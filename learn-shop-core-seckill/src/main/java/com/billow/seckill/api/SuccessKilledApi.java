package com.billow.seckill.api;

import com.billow.seckill.pojo.build.SuccessKilledBuildParam;
import com.billow.seckill.pojo.search.SuccessKilledSearchParam;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import org.springframework.web.bind.annotation.*;
import com.billow.seckill.service.SuccessKilledService;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀成功明细表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Slf4j
@Api(tags = {"SuccessKilledApi"}, value = "秒杀成功明细表")
@RestController
@RequestMapping("/successKilledApi")
public class SuccessKilledApi extends HighLevelApi<SuccessKilledService, SuccessKilledPo, SuccessKilledVo, SuccessKilledBuildParam, SuccessKilledSearchParam> {

}
