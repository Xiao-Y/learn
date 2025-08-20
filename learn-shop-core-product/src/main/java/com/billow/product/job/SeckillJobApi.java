package com.billow.product.job;

import com.billow.product.service.SeckillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "SeckillJobApi", description = "秒杀自动任务")
@RestController
@RequestMapping("/seckillJobApi")
public class SeckillJobApi {

    @Autowired
    private SeckillService seckillService;

    @Operation(summary = "自动任务加载数据到缓存中")
    @PostMapping("/loadSeckillJob")
    public void loadSeckillJob() {
        seckillService.loadSeckillJob();
    }
}
