package com.billow.common.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis工具类
 *
 * @author liuyongtao
 * @create 2018-05-24 11:32
 */
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
     * @param value
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
     * 将对象转为json 保存到缓存
     *
     * @param key
     * @param value
     * @author liuyongtao
     * @since 2021-1-30 10:37
     */
    public void setJson(String key, Object value) {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, JSON.toJSONString(value));
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
     * 将对象转为json 保存到缓存
     *
     * @param key
     * @param value
     * @author liuyongtao
     * @since 2021-1-30 10:37
     */
    public void setJson(String key, Object value, long l, TimeUnit timeUnit) {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, JSON.toJSONString(value), l, timeUnit);
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
    public <T> T getObj(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object value = ops.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return (T) value;
    }

    /**
     * 获取List类型的数据
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
     * @param k key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> Map<String, T> getHashAll(String k) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.entries(k);
    }

    /**
     * 通过  key 获取 map
     *
     * @param k key
     * @return {@link Map< String,List<T>>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> Map<String, List<T>> getHashAll(String k, Class<T> clazz) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.entries(k).entrySet().stream().collect(Collectors.toMap(m -> m.getKey(), v -> {
            JSONArray value = (JSONArray) v.getValue();
            List<T> object = JSONObject.parseArray(value.toJSONString(), clazz);
            return object;
        }));
    }


    /**
     * 通过 key 和 hash key 获取 map
     *
     * @param k hash key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> T getHash(String k, String hk) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(k, hk);
    }


    /**
     * 保存一个 map 到 hash 中
     *
     * @param k   key
     * @param map
     * @author liuyongtao
     * @since 2021-1-28 8:21
     */
    public <T> void setHash(String k, Map<String, T> map) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(k, map);
    }


    public <T> void setHash(String k, String hk, T v) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(k, hk, v);
    }


    public <T> void delHash(String k, String... hk) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.delete(k, hk);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public <T> List<String> getHashKeys(String k) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return new ArrayList<>(opsForHash.keys(k));
    }
}
