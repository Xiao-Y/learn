package com.billow.system.common.init.impl;

import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.service.PermissionService;
import com.billow.system.service.RoleService;
import com.billow.system.service.redis.RolePermissionRedisKit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

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
    private RolePermissionRedisKit rolePermissionRedisKit;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init Role Permission....");
        executorService.execute(() -> {
            List<RolePo> rolePos = roleService.list();
            for (RolePo rolePo : rolePos) {
                Set<PermissionPo> permissionPos = permissionService.findPermissionByRole(rolePo);
                rolePermissionRedisKit.setRolePermission(new ArrayList<>(permissionPos), rolePo.getRoleCode());
            }
            log.info("======== end init Role Permission....");
        });
        return true;
    }
}
