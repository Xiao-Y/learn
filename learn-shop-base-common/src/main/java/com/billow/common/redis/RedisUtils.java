package com.billow.common.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
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
    private RedisTemplate<String, String> redisTemplate;

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

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
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

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
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
//        Assert.notNull(value,"value is not empty");

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, JSONObject.toJSONString(value));
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

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, JSONObject.toJSONString(value), l, timeUnit);
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
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
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
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(key);
        T object = JSONObject.parseObject(value, clazz);
        return object;
    }

    /**
     * 获取List类型的数据（转List对象）
     *
     * @param key key
     * @return void
     * @author LiuYongTao
     * @date 2018/5/24 12:29
     */
    public <T> List<T> getArray(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(key);
        return JSONObject.parseArray(value, clazz);
    }
}
