package com.billow.gateway.security.service.redis;

import com.billow.gateway.pojo.po.UserPo;
import com.billow.gateway.pojo.po.UserRolePo;
import com.billow.redis.util.RedisUtils;
import com.billow.tools.constant.RedisCst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户相关的redis 操作
 *
 * @author liuyongtao
 * @create 2019-07-31 15:01
 */
@Component
public class UserRedisKit {

    @Autowired
    private RedisUtils redisUtils;

    public UserPo getUserInfoCache(String usercode) {
        return redisUtils.getHashObj(RedisCst.USER_INFO_KEY, usercode, UserPo.class);
    }

    public List<UserRolePo> getUserRoleCache(String usercode) {
        return redisUtils.getHash(RedisCst.USER_ROLE_KEY, usercode, UserRolePo.class);
    }
}
