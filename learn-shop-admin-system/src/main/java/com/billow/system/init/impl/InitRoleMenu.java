package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.RoleDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.properties.CustomProperties;
import com.billow.system.service.MenuService;
import com.billow.system.service.redis.CommonRoleMenuRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 初始化角色菜单,保存到 redis 中
 *
 * @author liuyongtao
 * @create 2019-05-23 19:34
 */
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

    @Override
    public boolean init() {
        if (!customProperties.getMenu().isWriteCache()) {
            return true;
        }
        List<RolePo> rolePos = roleDao.findAll();
        for (RolePo rolePo : rolePos) {
            Set<MenuEx> menuExs = menuService.findMenuByRole(rolePo);
            redisUtils.setObj(CommonRoleMenuRedis.ROLE_MENU_KEY + rolePo.getRoleCode(), menuExs);
        }
        return true;
    }
}
