package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.RoleDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.properties.CustomProperties;
import com.billow.system.service.MenuService;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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
    private RoleDao roleDao;
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
            List<RolePo> rolePos = roleDao.findAll();
            for (RolePo rolePo : rolePos) {
                Set<MenuEx> menuExs = menuService.findMenuByRole(rolePo);
                redisUtils.setObj(RedisCst.ROLE_MENU_KEY + rolePo.getRoleCode(), menuExs);
            }
            log.info("======== end init Role Menu....");
        });
        return true;
    }
}
