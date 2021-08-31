package com.billow.product.cache;

import com.billow.common.redis.RedisUtils;
import com.billow.product.pojo.cache.SeckillProductCacheDto;
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
public class SeckillProductCache {

    @Autowired
    private RedisUtils redisUtils;

    public void saveSeckillProductCache(Map<String, SeckillProductCacheDto> collect) {
        redisUtils.setHash(RedisCst.SECKILL_PRODUCT_INFO, collect);
    }
}
