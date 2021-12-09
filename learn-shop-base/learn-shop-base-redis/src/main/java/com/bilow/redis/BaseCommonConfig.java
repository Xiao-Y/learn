package com.bilow.redis;

import com.bilow.redis.util.RedisUtils;
import com.bilow.redis.util.RedissLockUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @since 2021-2-3 11:32
 */
@Configuration
public class BaseCommonConfig
{

    /**
     * redis分布式锁帮助类
     *
     * @return {@link RedissLockUtil}
     * @author liuyongtao
     * @since 2021-2-3 11:40
     */
    @Bean
    public RedissLockUtil redissLockUtil()
    {
        return new RedissLockUtil();
    }

    /**
     * redis工具类
     *
     * @return {@link RedisUtils}
     * @author liuyongtao
     * @since 2021-2-3 11:40
     */
    @Bean
    public RedisUtils redisUtils()
    {
        return new RedisUtils();
    }
}
