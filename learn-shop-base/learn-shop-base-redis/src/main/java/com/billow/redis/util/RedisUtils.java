package com.billow.redis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis工具类
 *
 * @author liuyongtao
 * @create 2018-05-24 11:32
 */
public class RedisUtils
{

    @Resource
    private RedisTemplate customRedisTemplate;


    /**
     * 获取String类型的数据转对象
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> T getObj(String key, Class<T> tClass)
    {
        Object json = customRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(json))
        {
            return null;
        }
        return JSON.parseObject(json.toString(), tClass);
    }

    /**
     * 获取object类型的数据
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public String getObj(String key)
    {
        ValueOperations<String, Object> ops = customRedisTemplate.opsForValue();
        Object value = ops.get(key);
        if (Objects.isNull(value))
        {
            return null;
        }
        return (String) value;
    }

    /**
     * 插入object类型的数据
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> void setObj(String key, T value)
    {
        Assert.notNull(key, "key is not empty");
        ValueOperations<String, Object> ops = customRedisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * 插入object类型的数据，设置失效时间
     *
     * @param key   key
     * @param value value
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> void setObj(String key, T value, long l, TimeUnit timeUnit)
    {
        Assert.notNull(key, "key is not empty");
        Assert.notNull(value, "value is not empty");
        Assert.notNull(timeUnit, "timeUnit is not empty");

        ValueOperations<String, Object> ops = customRedisTemplate.opsForValue();
        ops.set(key, value, l, timeUnit);
    }


    /**
     * 获取List类型的数据
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> List<T> getList(String key)
    {
        Object value = customRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(value))
        {
            return new ArrayList<>();
        }
        return JSON.parseObject(value.toString(), new TypeReference<List<T>>()
        {
        });
    }

    /**
     * 通过 key 获取所有 map 中的 value
     *
     * @param k key
     * @return {@link List<T>}
     * @author liuyongtao
     * @since 2021-1-28 8:20
     */
    public <T> List<T> getHashAllValue(String k, Class<T> tClass)
    {
        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(k);
        if (entries == null && entries.size() == 0)
        {
            return new ArrayList<>();
        }
        return entries.values()
                .parallelStream()
                .map(m -> {
                    if (m.startsWith("{"))
                    {
                        return Arrays.asList(JSON.parseObject(m, tClass));
                    }
                    else
                    {
                        return JSON.parseArray(m, tClass);
                    }
                }).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * 通过  key 获取 map
     *
     * @param k key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> Map<String, List<T>> getHashAll(String k, Class<T> clazz)
    {
        Map<String, List<T>> map = new HashMap<>();

        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(k);
        if (entries == null && entries.size() == 0)
        {
            return map;
        }
        return entries.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(m -> m.getKey(), v -> JSONObject.parseArray(v.getValue(), clazz)));
    }

    /**
     * 通过  key 获取 map
     *
     * @param k key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> Map<String, T> getHashAllObj(String k, Class<T> clazz)
    {
        Map<String, T> map = new HashMap<>();

        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(k);
        if (entries == null && entries.size() == 0)
        {
            return map;
        }
        return entries.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(m -> m.getKey(), v -> JSON.parseObject(v.getValue(), clazz)));
    }

    /**
     * 通过  key 获取 map
     *
     * @param k key
     * @return {@link Map< String,String>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public Map<String, String> getHashAllObj(String k)
    {
        Map<String, String> map = new HashMap<>();

        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(k);
        if (entries == null && entries.size() == 0)
        {
            return map;
        }
        return entries.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(m -> m.getKey(), v -> v.getValue()));
    }

    /**
     * 通过 key 和 hash key 获取 value
     *
     * @param k hash key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> List<T> getHash(String k, String hk, Class<T> clazz)
    {
        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        return JSONObject.parseArray(opsForHash.get(k, hk), clazz);
    }

    /**
     * 通过 key 和 hash key 获取 value
     *
     * @param k hash key
     * @return {@link Map< String,T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> T getHashObj(String k, String hk, Class<T> clazz)
    {
        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        return JSON.parseObject(opsForHash.get(k, hk), clazz);
    }

    /**
     * 保存一个 map 到 hash 中
     *
     * @param k   key
     * @param map
     * @author liuyongtao
     * @since 2021-1-28 8:21
     */
    public <T> void setHash(String k, Map<String, T> map)
    {
        HashOperations<String, String, T> opsForHash = customRedisTemplate.opsForHash();
        opsForHash.putAll(k, map);
    }


    public <T> void setHash(String k, String hk, T v)
    {
        HashOperations<String, String, T> opsForHash = customRedisTemplate.opsForHash();
        opsForHash.put(k, hk, v);
    }

    public <T> void delHash(String k, String... hk)
    {
        HashOperations<String, String, T> opsForHash = customRedisTemplate.opsForHash();
        opsForHash.delete(k, hk);
    }

    public void del(String key)
    {
        customRedisTemplate.delete(key);
    }

    public void del(Collection<String> keys)
    {
        customRedisTemplate.delete(keys);
    }

    public List<String> getHashKeys(String k)
    {
        HashOperations<String, String, String> opsForHash = customRedisTemplate.opsForHash();
        return new ArrayList<>(opsForHash.keys(k));
    }
}
