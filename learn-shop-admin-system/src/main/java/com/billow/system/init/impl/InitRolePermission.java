package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.RoleDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.service.PermissionService;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 初始化角色权限,保存到 redis 中
 *
 * @author liuyongtao
 * @create 2019-05-23 19:34
 */
@Slf4j
@Component
public class InitRolePermission implements IStartLoading {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionService permissionService;

    @Override
    @Async("fxbDrawExecutor")
    public boolean init() {
        log.info("======== start init Role Permission....");
        List<RolePo> rolePos = roleDao.findAll();
        for (RolePo rolePo : rolePos) {
            Set<PermissionPo> permissionPos = permissionService.findPermissionByRole(rolePo);
            redisUtils.setObj(RedisCst.ROLE_PERMISSION_KEY + rolePo.getRoleCode(), permissionPos);
        }
        log.info("======== end init Role Permission....");
        return true;
    }
}
