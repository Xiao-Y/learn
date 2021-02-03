package com.billow.common;

import com.billow.common.redis.RedisUtils;
import com.billow.common.redis.RedissLockUtil;
import com.billow.common.utils.UserTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @since 2021-2-3 11:32
 */
@Configuration
public class BaseCommonConfig {

    /**
     * redis分布式锁帮助类
     *
     * @return {@link RedissLockUtil}
     * @author liuyongtao
     * @since 2021-2-3 11:40
     */
    @Bean
    public RedissLockUtil redissLockUtil() {
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
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }

    @Bean
    public UserTools userTools() {
        return new UserTools();
    }
}
