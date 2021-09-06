package com.billow.product.cache;

import com.billow.common.redis.RedisUtils;
import com.billow.product.pojo.cache.SeckillCacheDto;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    private RedisUtils redisUtils;

    /**
     * 保存秒杀信息
     *
     * @param map 秒杀信息
     * @return {@link SeckillCacheDto}
     * @author liuyongtao
     * @since 2021-8-30 16:34
     */
    public void saveSeckillCache(Map<String, String> map) {
        redisUtils.setHash(RedisCst.SECKILL_INFO, map);
    }
}
