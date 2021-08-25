package com.billow.seckill.job;

import com.billow.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀自动任务
 *
 * @author liuyongtao
 * @since 2021-6-11 11:25
 */
@Slf4j
@Api(tags = {"SeckillJobApi"}, value = "秒杀自动任务")
@RestController
@RequestMapping("/seckillJobApi")
public class SeckillJobApi {

    @Autowired
    private SeckillService seckillService;

    @ApiOperation(value = "自动任务加载数据到缓存中")
    @PostMapping("/loadSeckillJob")
    public void loadSeckillJob() {
        seckillService.loadSeckillJob();
    }
}
