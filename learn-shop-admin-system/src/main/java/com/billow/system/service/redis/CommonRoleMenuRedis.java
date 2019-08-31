package com.billow.system.service.redis;

import com.billow.common.redis.RedisUtils;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 操作 redis 中的角色和角色数据
 *
 * @author liuyongtao
 * @create 2019-07-16 15:05
 */
@Service
public class CommonRoleMenuRedis {

    public final static String ROLE_MENU_KEY = RedisCst.ROLE_MENU_KEY;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 更新角色CODE
     *
     * @param newRoleCode
     * @param oldRoleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:45
     */
    public void updateRoleCode(String newRoleCode, String oldRoleCode) {
        if (newRoleCode.equals(oldRoleCode)) {
            return;
        }
        List<MenuEx> menuExs = redisUtils.getArray(ROLE_MENU_KEY + oldRoleCode, MenuEx.class);
        this.deleteRoleByRoleCode(oldRoleCode);
        redisUtils.setObj(ROLE_MENU_KEY + newRoleCode, menuExs);
    }

    /**
     * 删除指定角色的菜单信息
     *
     * @param roleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:09
     */
    public void deleteRoleByRoleCode(String roleCode) {
        redisTemplate.delete(ROLE_MENU_KEY + roleCode);
    }

    /**
     * 删除 redis 所有角色所持有的该角色的菜单
     *
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:02
     */
    public void deleteRoleMenuById(Set<String> ids) {
        Set<String> menuKeys = redisTemplate.keys(ROLE_MENU_KEY + "*");
        menuKeys.stream().forEach(f -> {
            List<MenuEx> menuExes = redisUtils.getArray(f, MenuEx.class);

            List<MenuEx> voList = menuExes.stream()
                    .filter(fi -> !ids.contains(fi.getId()))
                    .collect(Collectors.toList());
            redisUtils.setObj(f, voList);
        });
    }

    /**
     * 通过角色 id 更新角色信息
     *
     * @param menuPo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:46
     */
    public void updateMeunById(MenuPo menuPo) {
        Set<String> menuKeys = redisTemplate.keys(ROLE_MENU_KEY + "*");
        menuKeys.stream().forEach(f -> {
            List<MenuEx> menuExes = redisUtils.getArray(f, MenuEx.class);

            List<MenuEx> voList = menuExes.stream()
                    .filter(fi -> !new Long(fi.getId()).equals(menuPo.getId()))
                    .collect(Collectors.toList());
            voList.add(this.menuPoCoverMenuex(menuPo));
            redisUtils.setObj(f, voList);
        });
    }

    /**
     * 更新指定角色的角色信息
     *
     * @param menuPos
     * @param roleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:57
     */
    public void updateRoleMenuByRoleCode(List<MenuPo> menuPos, String roleCode) {
        List<MenuEx> exs = new ArrayList<>();
        menuPos.forEach(item -> exs.add(this.menuPoCoverMenuex(item)));
        redisUtils.setObj(ROLE_MENU_KEY + roleCode, exs);
    }

    public List<MenuEx> findMenusByRoles(List<RoleVo> roleVos) {

        List<MenuEx> menuExes = new ArrayList<>();

        Set<MenuEx> all = new HashSet<>();
        roleVos.stream().forEach(f -> {
            List<MenuEx> menuPos = redisUtils.getArray(ROLE_MENU_KEY + f.getRoleCode(), MenuEx.class);
            if (ToolsUtils.isNotEmpty(menuPos)) {
                all.addAll(menuPos);
            }
        });

        if (ToolsUtils.isEmpty(all)) {
            return null;
        }

        // 指出所有的父菜单
        all.stream().filter(f -> f.getPid() == null).forEach(f -> {
            MenuEx menuEx = new MenuEx();
            ConvertUtils.convert(f, menuEx);
            // 查询出子菜单
            this.findChildren(all, menuEx);
            menuExes.add(menuEx);
        });
        menuExes.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
        return menuExes;
    }

    /**
     * 查询指定 id 的子节点
     *
     * @param all
     * @param menuEx
     * @return java.util.List<com.billow.system.pojo.ex.MenuEx>
     * @author LiuYongTao
     * @date 2019/7/23 10:07
     */
    private void findChildren(Set<MenuEx> all, MenuEx menuEx) {

        List<MenuEx> collect = all.stream().filter(f -> (f.getPid() != null && f.getPid().equals(new Long(menuEx.getId()))))
                .collect(Collectors.toList());

        if (ToolsUtils.isNotEmpty(collect)) {
            collect.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
            menuEx.setChildren(collect);
            collect.stream().filter(f -> f.getDisplay()).forEach(f -> menuEx.setIsChildrenDisplay(true));
            collect.stream().forEach(f -> this.findChildren(all, f));
        }
    }

    /**
     * MenuPo 转换成 MenuEx
     *
     * @param item
     * @return com.billow.system.pojo.ex.MenuEx
     * @author LiuYongTao
     * @date 2019/7/23 10:52
     */
    public MenuEx menuPoCoverMenuex(MenuPo item) {
        MenuEx ex = new MenuEx();
        ex.setId(item.getId().toString());
        ex.setPath("");
        ex.setValidInd(item.getValidInd());
        ex.setIcon(item.getIcon());
        ex.setPid(item.getPid());
        ex.setDisplay(item.getDisplay());
        ex.setTitleCode(item.getMenuCode());
        ex.setTitle(item.getMenuName());
        ex.setSortField(item.getSortField());
        return ex;
    }
}
