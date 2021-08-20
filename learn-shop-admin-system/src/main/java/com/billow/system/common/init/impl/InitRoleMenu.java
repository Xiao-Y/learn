package com.billow.system.common.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.common.properties.CustomProperties;
import com.billow.system.service.MenuService;
import com.billow.system.service.RoleService;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 初始化角色菜单,保存到 redis 中
 *
 * @author liuyongtao
 * @create 2019-05-23 19:34
 */
@Slf4j
@Component
public class InitRoleMenu implements IStartLoading {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CustomProperties customProperties;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init Role Menu....");
        executorService.execute(() -> {
            if (!customProperties.getMenu().isWriteCache()) {
                log.info("======== end not init Role Menu....");
                return;
            }
            Map<String, List<MenuEx>> map = new HashMap<>();
            List<RolePo> rolePos = roleService.list();
            for (RolePo rolePo : rolePos) {
                List<MenuEx> menuExs = menuService.findMenuByRole(rolePo);
                map.put(rolePo.getRoleCode(), menuExs);
            }
            redisUtils.setHash(RedisCst.ROLE_MENU_KEY, map);
            log.info("======== end init Role Menu....");
        });
        return true;
    }
}
