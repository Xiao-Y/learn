package com.billow.gateway.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     * 通过 key 和 hash key 获取 map
     *
     * @param k hash key
     * @return {@link List< T>}
     * @author liuyongtao
     * @since 2021-1-28 8:24
     */
    public <T> List<T> getHash(String k, String HK, Class<T> clazz) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        JSONArray value = (JSONArray) opsForHash.get(k, HK);
        List<T> list = this.converJsonArray2List(clazz, value);
        return list;
    }

    /**
     * json 反序列化时异常处理
     *
     * @param clazz
     * @param value
     * @return {@link List<T>}
     * @author liuyongtao
     * @since 2021-1-30 16:59
     */
    private <T> List<T> converJsonArray2List(Class<T> clazz, JSONArray value) {
        List<T> list = new ArrayList<>();
        for (Object o : value) {
            Map<String, Object> map = BeanUtil.beanToMap(o);
            map.remove("@type");
            list.add(BeanUtil.mapToBean(map, clazz, true, CopyOptions.create()));
        }
        return list;
    }
}
