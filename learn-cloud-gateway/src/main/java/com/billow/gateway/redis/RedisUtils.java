package com.billow.gateway.redis;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author liuyongtao
 * @create 2018-05-24 11:32
 */
@Component
public class RedisUtils {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 插入string类型的数据
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public void setString(String key, String value) {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * 插入string类型的数据，设置失效时间
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public void setString(String key, String value, long l, TimeUnit timeUnit) {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");
        Assert.notNull(timeUnit, "timeUnit is not empty");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value, l, timeUnit);
    }

    /**
     * 插入object类型的数据（转json）
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> void setObj(String key, T value) {
        Assert.notNull(key, "key is not empty");
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * 插入object类型的数据，设置失效时间（转json）
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> void setObj(String key, T value, long l, TimeUnit timeUnit) {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");
        Assert.notNull(timeUnit, "timeUnit is not empty");

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value, l, timeUnit);
    }

    /**
     * 获取string类型的数据
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public String getString(String key) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 获取object类型的数据（转对象）
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> T getObj(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value = ops.get(key);
        T object = JSONObject.parseObject(value, clazz);
        return object;
    }

    /**
     * 获取object类型的数据（转对象）
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> T getObj(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object value = ops.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return (T) value;
    }

    /**
     * 获取List类型的数据（转List对象）
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value = ops.get(key);
        return JSONObject.parseArray(value, clazz);
    }

    /**
     * 获取List类型的数据（转List对象）
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> List<T> getList(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object value = ops.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        if (Set.class.isAssignableFrom(value.getClass())) {
            return new ArrayList<T>((Set) value);
        }
        return (List<T>) value;
    }

    /**
     * 通过 hash key 获取 map 中的 value
     *
     * @param H hash key
     * @return {@link List<T>}
     * @author liuyongtao
     * @since 2021-1-28 8:20
     */
    public <T> List<T> getHashAllValue(String H) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        Map<String, T> entries = opsForHash.entries(H);
        if (MapUtils.isEmpty(entries)) {
            return null;
        }
        return new ArrayList<>(entries.values());
    }

    /**
     * 通过  key 获取 map
     *
     * @param K key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> Map<String, T> getHashAll(String K) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.entries(K);
    }

    /**
     * 通过 key 和 hash key 获取 map
     *
     * @param k hash key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> T getHash(String k, String HK) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(k, HK);
    }

    /**
     * 保存一个 map 到 hash 中
     *
     * @param K   key
     * @param map
     * @author liuyongtao
     * @since 2021-1-28 8:21
     */
    public <T> void setHash(String K, Map<String, T> map) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(K, map);
    }


    public <T> void setHash(String K, String HK, T v) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(K, HK, v);
    }
}
