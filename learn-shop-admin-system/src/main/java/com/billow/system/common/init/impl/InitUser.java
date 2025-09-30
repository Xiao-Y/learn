package com.billow.system.common.init.impl;

import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.UserPo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.service.UserRoleService;
import com.billow.system.service.UserService;
import com.billow.system.service.redis.UserRedisKit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 千面
 * @date 2025-08-21 11:13:39
 */
@Slf4j
@Component
public class InitUser implements IStartLoading {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;
    @Autowired
    private UserRedisKit userRedisKit;

    @Override
    public boolean init() {
        log.info("======== start init User....");
        executorService.execute(() -> {

            List<UserPo> list = userService.list();

            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            // 缓存用户信息
            Map<Long, List<UserRolePo>> userRoleMapUserId = userRoleService.lambdaQuery()
                    .in(UserRolePo::getUserId, list.stream()
                            .map(UserPo::getId)
                            .collect(Collectors.toList()))
                    .list()
                    .stream()
                    .collect(Collectors.groupingBy(UserRolePo::getUserId));

            Map<String, UserPo> userPoMapUsercode = list.stream()
                    .collect(Collectors.toMap(UserPo::getUsercode, Function.identity(), (k1, k2) -> k1));
            userRedisKit.setUserInfoCache(userPoMapUsercode);

            // 缓存用户角色
            if (MapUtils.isNotEmpty(userRoleMapUserId)) {
                Map<String, List<UserRolePo>> userRoleMapUsercode = new HashMap<>();
                for (UserPo userPo : list) {
                    List<UserRolePo> userRolePos = userRoleMapUserId.get(userPo.getId());
                    if (CollectionUtils.isNotEmpty(userRolePos)) {
                        userRoleMapUsercode.put(userPo.getUsercode(), userRolePos);
                    }
                }
                userRedisKit.setUserRoleCache(userRoleMapUsercode);
            }

            log.info("======== end init User....");
        });
        return true;
    }
}
