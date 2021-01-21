package com.billow.mybatis.cache;

import com.billow.tools.utlis.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache {

    private static final Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);

    private String id; // cache instance id

    private static long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private RedisTemplate redisTemplate;

    public MybatisRedisCache(String id) {
        this.id = id;
    }

    private RedisTemplate<Object, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            try {
                redisTemplate = SpringContextUtil.getBean("redisCacheTemplate");
                String property = SpringContextUtil.getApplicationContext().getEnvironment().getProperty("spring.redis.cacheExpire");
                if (property != null && "".equals(property)) {
                    EXPIRE_TIME_IN_MINUTES = Long.parseLong(property);
                }
            } catch (Exception e) {
                log.error("get RedisTemplate exception:", e);
            }
        }
        return redisTemplate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.boundHashOps(getId()).put(key, value);
        redisTemplate.boundHashOps(getId()).expire(EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
        log.info("Put query result to redis");
//        log.debug("key=[{}],value=[{}]", key, value);

    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        Object value = redisTemplate.boundHashOps(getId()).get(key);
        log.info("Get cached query result from redis");
//        log.debug("key=[{}],value=[{}]", key, value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        Object value = redisTemplate.boundHashOps(getId()).delete(key);
        log.info("Remove cached query result from redis");
//        log.debug("key=[{}],value=[{}]", key, value);
        return value;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(getId());
        log.info("Clear all the cached query result from redis");
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = getRedisTemplate();
        Long size = redisTemplate.boundHashOps(getId()).size();
        return size == null ? 0 : size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
