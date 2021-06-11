package com.billow.seckill.test;

import com.billow.seckill.cache.SeckillCache;
import com.billow.seckill.enums.SeckillStatEnum;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.tools.utlis.FieldUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongtao
 * @since 2021-6-11 16:40
 */
@Slf4j
@Api(tags = {"SeckillApp"}, value = "测试Lua")
@RestController
@RequestMapping("/seckillApp")
public class TestLuaApp {

    @Autowired
    private SeckillCache seckillCache;

    @ApiOperation(value = "测试Lua")
    @GetMapping(value = "/testLua/{seckillId}/{userCode}")
    public SeckillStatEnum testLua(@PathVariable("seckillId") Long seckillId,
                                   @PathVariable("userCode") String userCode) {
        // 构建订单数据
        SuccessKilledPo killedPo = new SuccessKilledPo();
        killedPo.setSeckillId(seckillId);
        killedPo.setUsercode(userCode);
        killedPo.setKillState(SeckillStatEnum.SUCCESS.getState());
        FieldUtils.setCommonFieldByInsert(killedPo, userCode);
        // 执行秒杀
        SeckillStatEnum statEnum = seckillCache.executeSeckill1(killedPo);
        return statEnum;
    }
}
