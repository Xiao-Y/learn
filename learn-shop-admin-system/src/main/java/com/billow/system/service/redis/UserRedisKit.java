package com.billow.system.service.redis;

import com.billow.redis.util.RedisUtils;
import com.billow.system.dao.UserDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.po.UserPo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.tools.constant.RedisCst;
import org.apache.commons.collections4.CollectionUtils;
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
public class UserRedisKit {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

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

    /**
     * 更新用户信息缓存和用户角色缓存
     *
     * @param usercode
     * @author 千面
     * @date 2025-08-21 15:09:56
     */
    public void updateUserInfoCache(String usercode) {
        // 删除缓存
        redisUtils.delHash(RedisCst.USER_INFO_KEY, usercode);
        redisUtils.delHash(RedisCst.USER_ROLE_KEY, usercode);

        // 获取用户信息
        UserPo userPo = userDao.findByUsercode(usercode);
        if (userPo == null) {
            return;
        }
        redisUtils.setHash(RedisCst.USER_INFO_KEY, usercode, userPo);

        // 获取用户角色
        List<UserRolePo> userRolePos = userRoleDao.findByUserIdIsAndValidIndIsTrue(userPo.getId());
        if (CollectionUtils.isNotEmpty(userRolePos)) {
            redisUtils.setHash(RedisCst.USER_ROLE_KEY, usercode, userRolePos);
        }
    }
}
