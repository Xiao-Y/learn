package com.billow.order.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.billow.order.pojo.po.SuccessKilledPo;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 秒杀缓存操作
 *
 * @author liuyongtao
 * @since 2021-6-11 10:11
 */
@Slf4j
@Component
@RefreshScope
public class SeckillCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 秒杀用户key
     *
     * @param seckillId 秒杀id
     * @param userCode  用户code
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:05
     */
    public String genSeckillLockKey(Long seckillId, String userCode) {
        return RedisCst.genKey(RedisCst.SECKILL_LOCK, seckillId.toString(), userCode);
    }

    /**
     * 获取秒杀成功的数据
     *
     * @param seckillId
     * @param userCode
     * @return {@link SuccessKilledPo}
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public SuccessKilledPo findSuccessKilledCache(Long seckillId, String userCode) {
        String seckillLockKey = this.genSeckillLockKey(seckillId, userCode);
        String killedPoJson = redisTemplate.opsForValue().get(seckillLockKey);
        return JSONObject.parseObject(killedPoJson, SuccessKilledPo.class);
    }
}
