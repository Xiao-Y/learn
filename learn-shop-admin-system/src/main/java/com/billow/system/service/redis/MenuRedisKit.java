package com.billow.system.service.redis;

import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.tools.constant.RedisCst;
import com.billow.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 菜单查询缓存帮助类
 *
 * @author liuyongtao
 * @since 2021-1-30 9:57
 */
@Service
public class MenuRedisKit {

    private final static String MENU_TREE = RedisCst.MENU_MENU_TREE;
    private final static String MENU_ID = RedisCst.MENU_MENU_ID;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取菜单树
     *
     * @return {@link List< MenuEx>}
     * @author liuyongtao
     * @since 2021-1-30 10:28
     */
    public List<MenuEx> getMenusTree() {
        return redisUtils.getList(MENU_TREE);
    }

    /**
     * 保存菜单树到缓存
     *
     * @param pMenuExs
     * @author liuyongtao
     * @since 2021-1-30 10:33
     */
    public List<MenuEx> setMenusTree(List<MenuEx> pMenuExs) {
        redisUtils.setObj(MENU_TREE, pMenuExs);
        return pMenuExs;
    }

    /**
     * 删除菜单树到缓存
     *
     * @author liuyongtao
     * @since 2021-1-30 10:33
     */
    public void delMenusTree() {
        redisUtils.del(MENU_TREE);
    }

    /**
     * 查询缓存中的菜单信息
     *
     * @param id
     * @return {@link MenuVo}
     * @author liuyongtao
     * @since 2021-1-30 10:53
     */
    public MenuVo getMenuById(Long id) {
        return redisUtils.getHashObj(MENU_ID, id.toString(),MenuVo.class);
    }

    /**
     * 查询缓存中的菜单信息
     *
     * @param menuVo 必须要有 id
     * @return {@link MenuVo}
     * @author liuyongtao
     * @since 2021-1-30 10:53
     */
    public MenuVo setMenuById(MenuVo menuVo) {
        redisUtils.setHash(MENU_ID, menuVo.getId().toString(), menuVo);
        return menuVo;
    }

    /**
     * 更新菜单信息
     *
     * @param menuVo
     * @return {@link MenuVo}
     * @author liuyongtao
     * @since 2021-1-30 11:07
     */
    public MenuVo updateMenuById(MenuVo menuVo) {
        redisUtils.setHash(MENU_ID, menuVo.getId().toString(), menuVo);
        return menuVo;
    }

    /**
     * 删除菜单缓存信息
     *
     * @param ids
     * @author liuyongtao
     * @since 2021-1-30 11:12
     */
    public void delMenuByIds(Collection<String> ids) {
        redisUtils.delHash(MENU_ID, ids.toArray(new String[ids.size()]));
    }
}
