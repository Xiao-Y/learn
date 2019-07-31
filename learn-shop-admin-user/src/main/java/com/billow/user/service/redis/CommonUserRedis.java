package com.billow.user.service.redis;

import com.billow.common.redis.RedisUtils;
import com.billow.tools.constant.RedisCst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户相关的redis 操作
 *
 * @author liuyongtao
 * @create 2019-07-31 15:01
 */
@Component
public class CommonUserRedis {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 修改过用户信息的保存到黑名单中。保存 60 天
     *
     * @param oldUsercode
     * @param newUsercode
     * @param roleCodes
     * @return void
     * @author LiuYongTao
     * @date 2019/7/31 15:17
     */
    public void setBlacklistOnEditUser(String oldUsercode, String newUsercode, List<String> roleCodes) {
        Map obj = redisUtils.getObj(RedisCst.BLACKLIST_EDITUSER + oldUsercode, Map.class);
        List<String> oldUser;
        if (obj == null) {
            obj = new HashMap<>();
            oldUser = new ArrayList<>();
            oldUser.add(oldUsercode);
        } else {
            oldUser = ((Map<String, List<String>>) obj).get(RedisCst.BLACKLIST_EDITUSER_OLDUSER);
            if (oldUser == null) {
                oldUser = new ArrayList<>();
            }
            oldUser.add(oldUsercode);

        }
        obj.put(RedisCst.BLACKLIST_EDITUSER_OLDUSER, oldUser);
        obj.put(RedisCst.BLACKLIST_EDITUSER_ROLECODES, roleCodes);
        // 更新所有在60内所使用的 usecode 的角色为最新，防止使用旧 token 访问
        for (String userCode : oldUser) {
            redisUtils.setObj(RedisCst.BLACKLIST_EDITUSER + userCode, obj, 60, TimeUnit.DAYS);
        }
        // 设置新的为60天
        redisUtils.setObj(RedisCst.BLACKLIST_EDITUSER + newUsercode, obj, 60, TimeUnit.DAYS);
    }
}
