package com.billow.promotion.cache;

import com.billow.promotion.pojo.cache.SeckillSessionCacheDto;
import com.billow.tools.constant.RedisCst;
import com.bilow.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
public class SeckillSessionCache {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取秒杀场次信息
     *
     * @param seckillSessionId 秒杀场次id
     * @return {@link SeckillSessionCacheDto}
     * @author liuyongtao
     * @since 2021-8-30 16:34
     */
    public SeckillSessionCacheDto getSeckillSessionCache(Long seckillSessionId) {
        return redisUtils.getHash(RedisCst.SECKILL_SESSION, seckillSessionId.toString(), SeckillSessionCacheDto.class);
    }
}
