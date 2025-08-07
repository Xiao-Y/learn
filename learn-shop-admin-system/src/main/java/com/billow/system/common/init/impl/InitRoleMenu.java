package com.billow.system.common.init.impl;

import com.billow.redis.util.RedisUtils;
import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.service.MenuService;
import com.billow.system.service.RoleService;
import com.billow.tools.constant.RedisCst;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init Role Menu....");
        executorService.execute(() -> {
            Map<String, List<MenuPo>> map = new HashMap<>();
            List<RolePo> rolePos = roleService.list();
            for (RolePo rolePo : rolePos) {
                List<MenuPo> menuPos = menuService.findMenuByRole(rolePo);
                map.put(rolePo.getRoleCode(), menuPos);
            }
            redisUtils.setHash(RedisCst.ROLE_MENU_KEY, map);
            log.info("======== end init Role Menu....");
        });
        return true;
    }
}
