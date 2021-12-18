package com.billow.promotion.cache;

import com.billow.promotion.pojo.cache.SeckillCacheDto;
import com.billow.tools.constant.RedisCst;
import com.billow.redis.util.RedisUtils;
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
public class SeckillCache
{

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取秒杀信息
     *
     * @param seckillId 秒杀id
     * @return {@link SeckillCacheDto}
     * @author liuyongtao
     * @since 2021-8-30 16:34
     */
    public SeckillCacheDto getSeckillCache(Long seckillId) {
        return redisUtils.getHashObj(RedisCst.SECKILL_INFO, seckillId.toString(), SeckillCacheDto.class);
    }
}
