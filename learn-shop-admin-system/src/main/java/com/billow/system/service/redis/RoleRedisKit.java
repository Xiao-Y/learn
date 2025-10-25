package com.billow.system.service.redis;

import com.billow.redis.util.RedisUtils;
import com.billow.system.dao.RoleDao;
import com.billow.system.pojo.po.RolePo;
import com.billow.tools.constant.RedisCst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    @Autowired
    private RoleDao roleDao;

    public void setRoleInfoCache(Map<String, RolePo> rolePoMapId) {
        redisUtils.setHash(RedisCst.ROLE_INFO_KEY, rolePoMapId);
    }

    public void updateRoleInfoCache(Long id) {
        RolePo one = roleDao.selectOneById(id);
        redisUtils.setHash(RedisCst.ROLE_INFO_KEY, id.toString(), one);
    }

    public void deleteRoleById(Long id) {
        redisUtils.delHash(RedisCst.ROLE_INFO_KEY, id.toString());
    }
}
