package com.billow.common.base;

import com.alibaba.fastjson.JSONObject;
import com.billow.tools.enums.RdsKeyEnum;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 公用control
 *
 * @author liuyongtao
 * @create 2018-05-19 14:40
 */
public class BaseApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    protected String applicationName;
    //    @Value("${eureka.instance.instance-id}")
    protected String instanceId;

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    /**
     * 向redis中添加值
     *
     * @param key    关键字
     * @param object 数据源
     */
    protected void setRedisObject(String key, Object object) {
        try {
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(object), 5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 获取redis的值,普通类型
     *
     * @param key   关键字
     * @param clazz 类型
     * @param <T>
     * @return
     */
    protected <T> T getRedisValue(String key, Class<T> clazz) {
        T t = null;
        try {
            String json = redisTemplate.opsForValue().get(key);
            t = JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return t;
    }

    /**
     * 获取redis的值,List类型
     *
     * @param key   关键字
     * @param clazz 类型
     * @param <T>
     * @return
     */
    protected <T> List<T> getRedisValues(String key, Class<T> clazz) {
        List<T> ts = null;
        try {
            String json = redisTemplate.opsForValue().get(key);
            ts = JSONObject.parseArray(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ts;
    }

    /**
     * 向redis中添加值
     *
     * @param rdsKeyEnum 关键字
     * @param object     数据源
     */
    protected void setRedisObject(RdsKeyEnum rdsKeyEnum, Object object) {
        try {
            redisTemplate.opsForValue().set(rdsKeyEnum.getKey(), JSONObject.toJSONString(object), 5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 获取redis的值,普通类型
     *
     * @param rdsKeyEnum 关键字
     * @param clazz      类型
     * @param <T>
     * @return
     */
    protected <T> T getRedisValue(RdsKeyEnum rdsKeyEnum, Class<T> clazz) {
        T t = null;
        try {
            String json = redisTemplate.opsForValue().get(rdsKeyEnum.getKey());
            t = JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return t;
    }

    /**
     * 获取redis的值,List类型
     *
     * @param rdsKeyEnum 关键字
     * @param clazz      类型
     * @param <T>
     * @return
     */
    protected <T> List<T> getRedisValues(RdsKeyEnum rdsKeyEnum, Class<T> clazz) {
        List<T> ts = null;
        try {
            String json = redisTemplate.opsForValue().get(rdsKeyEnum.getKey());
            ts = JSONObject.parseArray(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ts;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
