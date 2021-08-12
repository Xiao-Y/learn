package com.billow.seckill.app;

import com.billow.common.base.BaseApi;
import com.billow.common.utils.UserTools;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;
import com.billow.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(tags = {"SeckillApp"}, value = "秒杀库存表")
@RestController
@RequestMapping("/seckillApp")
public class SeckillApp extends BaseApi {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private UserTools userTools;

    @ApiOperation(value = "生成秒杀链接")
    @GetMapping(value = "/genSeckillUrl/{seckillId}")
    public ExposerVo genSeckillUrl(@PathVariable("seckillId") Long seckillId) {
        return seckillService.genSeckillUrl(seckillId);
    }

    @ApiOperation(value = "执行秒杀")
    @PostMapping(value = "/executionSeckill/{seckillId}")
    public SeckillExecutionVo executionSeckill(@PathVariable("seckillId") Long seckillId,
                                               @RequestParam("userCode") String userCode,
                                               @RequestParam("md5") String md5) {
//        String userCode = userTools.getCurrentUserCode();
        return seckillService.executionSeckill(seckillId, md5, userCode);
    }
}
