package com.billow.gateway.api;

import com.billow.gateway.receive.DataRecoveryReceive;
import com.billow.redis.util.RedisUtils;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据恢复
 *
 * @author liuyongtao
 * @create 2019-08-11 10:25
 */
@Slf4j
@RestController
@Api("数据恢复,不经过 jwt 验证")
@RequestMapping("/dataRecovery")
public class DataRecoveryApi {

    @Autowired
    private DataRecoveryReceive dataRecoveryReceive;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "数据恢复")
    @GetMapping("/initData")
    public Map<String, String> initData() {
        Map<String, String> map = new HashMap<>();
        map.put("resCode", "0000");
        // 检查是否在执行中
        String redisDataRecovery = redisUtils.getObj(RedisCst.COMM_DATA_RECOVERY);
        if (ToolsUtils.isNotEmpty(redisDataRecovery)) {
            log.info("正在执行数据恢复...");
            map.put("resMsg", "正在执行数据恢复...");
            return map;
        }

        log.info("开始数据恢复...");
        // 10 分钟内不在执行
//        redisUtils.setObj(RedisCst.COMM_DATA_RECOVERY, "正在执行数据恢复", 100, TimeUnit.MINUTES);
        map.put("resMsg", "开始数据恢复...");
        try {
            dataRecoveryReceive.dataRecovery();
            map.put("resTimestamp", DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS"));
        } catch (Exception e) {
            map.put("resCode", "0014");
            map.put("resMsg", "消息中间件异常");
        }
        log.info("消息发送完成...");
        return map;
    }
}
