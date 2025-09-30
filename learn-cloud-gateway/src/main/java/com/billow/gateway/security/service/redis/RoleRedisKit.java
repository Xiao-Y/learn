package com.billow.gateway.security.service.redis;

import com.billow.gateway.pojo.po.RolePo;
import com.billow.redis.util.RedisUtils;
import com.billow.tools.constant.RedisCst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户相关的redis 操作
 *
 * @author liuyongtao
 * @create 2019-07-31 15:01
 */
@Component
public class RoleRedisKit {

    @Autowired
    private RedisUtils redisUtils;

    public RolePo getRoleInfoCache(Long id) {
        return redisUtils.getHashObj(RedisCst.ROLE_INFO_KEY, id.toString(), RolePo.class);
    }
}
