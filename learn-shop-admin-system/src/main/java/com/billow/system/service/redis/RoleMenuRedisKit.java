package com.billow.system.service.redis;

import com.billow.redis.util.RedisUtils;
//import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 操作 redis 中的角色和角色数据
 *
 * @author liuyongtao
 * @create 2019-07-16 15:05
 */
@Service
public class RoleMenuRedisKit {

    public final static String ROLE_MENU_KEY = RedisCst.ROLE_MENU_KEY;

    @Autowired
    private RedisUtils redisUtils;

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
        if (Objects.equals(newRoleCode, oldRoleCode)) {
            return;
        }
        List<MenuPo> menuPos = redisUtils.getHash(ROLE_MENU_KEY, oldRoleCode, MenuPo.class);
        this.deleteRoleByRoleCode(oldRoleCode);
        redisUtils.setHash(ROLE_MENU_KEY, newRoleCode, menuPos);
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
        redisUtils.delHash(ROLE_MENU_KEY, roleCode);
    }

    /**
     * 删除 redis 所有角色所持有的该角色的菜单
     *
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:02
     */
    public void deleteRoleMenuByIds(Set<String> ids) {
        // 查询出所有 角色的菜单
        Map<String, List<MenuPo>> hashAll = redisUtils.getHashAll(ROLE_MENU_KEY, MenuPo.class);
        hashAll.entrySet().stream().forEach(f -> {
            List<MenuPo> voList = f.getValue().stream()
                    .filter(fi -> !ids.contains(fi.getId()))
                    .collect(Collectors.toList());
            // 过滤后重新填入
            redisUtils.setHash(ROLE_MENU_KEY, f.getKey(), voList);
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
    public void updateMenuById(MenuPo menuPo) {
        // 查询出所有 角色的菜单
        Map<String, List<MenuPo>> hashAll = redisUtils.getHashAll(ROLE_MENU_KEY, MenuPo.class);
        hashAll.entrySet().stream().forEach(f -> {
            List<MenuPo> voList = f.getValue().stream()
                    .filter(fi -> !menuPo.getId().equals(fi.getId()))
                    .collect(Collectors.toList());
            // 加入新的
            voList.add(menuPo);
            // 过滤后重新填入
            redisUtils.setHash(ROLE_MENU_KEY, f.getKey(), voList);
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
        redisUtils.setHash(ROLE_MENU_KEY, roleCode, menuPos);
    }

//    /**
//     * 带层级关系的菜单树
//     *
//     * @param roleVos
//     * @return {@link List< MenuEx>}
//     * @author xiaoy
//     * @since 2021/12/25 21:29
//     */
//    public List<MenuEx> findMenusByRoles(List<RoleVo> roleVos) {
//        // 读取缓存中的数据
//        List<MenuEx> all = new ArrayList<>();
//        roleVos.stream()
//                .map(RoleVo::getRoleCode)
//                .distinct()
//                .forEach(f -> {
//                    List<MenuPo> menuPos = redisUtils.getHash(ROLE_MENU_KEY, f, MenuPo.class);
//                    if (ToolsUtils.isNotEmpty(menuPos)) {
//                        all.addAll(menuPos);
//                    }
//                });
//        // 没有数据直接返回
//        if (ToolsUtils.isEmpty(all)) {
//            return null;
//        }
//        // 构建菜单层级
//        List<MenuEx> menuExes = new ArrayList<>();
//        // 指出所有的父菜单
//        all.stream().filter(f -> f.getPid() == null).forEach(f -> {
//            MenuEx menuEx = new MenuEx();
//            ConvertUtils.convert(f, menuEx);
//            // 查询出子菜单
//            this.findChildren(all, menuEx);
//            menuExes.add(menuEx);
//        });
//        menuExes.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
//        return menuExes;
//    }
//
//    /**
//     * 查询指定 id 的子节点
//     *
//     * @param all
//     * @param menuEx
//     * @return java.util.List<com.billow.system.pojo.ex.MenuEx>
//     * @author LiuYongTao
//     * @date 2019/7/23 10:07
//     */
//    private void findChildren(List<MenuEx> all, MenuEx menuEx) {
//        List<MenuEx> collect = all.stream()
//                .filter(f -> (f.getPid() != null && f.getPid().equals(new Long(menuEx.getId()))))
//                .collect(Collectors.toList());
//        if (ToolsUtils.isNotEmpty(collect)) {
//            collect.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
//            menuEx.setChildren(collect);
//            collect.stream().filter(f -> f.getDisplay()).forEach(f -> menuEx.setIsChildrenDisplay(true));
//            collect.stream().forEach(f -> this.findChildren(all, f));
//        }
//    }

    /**
     * 不带层级关系的菜单树
     *
     * @param roleVos
     * @return {@link List< MenuPo>}
     * @author xiaoy
     * @since 2021/12/25 21:29
     */
    public List<MenuPo> findMenuListByRoles(List<RoleVo> roleVos) {
        // 读取缓存中的数据
        return roleVos.stream()
                .map(RoleVo::getRoleCode)
                .distinct()
                .map(m -> redisUtils.getHash(ROLE_MENU_KEY, m, MenuPo.class))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
//
//    /**
//     * MenuPo 转换成 MenuEx
//     *
//     * @param item
//     * @return com.billow.system.pojo.ex.MenuEx
//     * @author LiuYongTao
//     * @date 2019/7/23 10:52
//     */
//    public MenuEx menuPoCoverMenuex(MenuPo item) {
//        MenuEx ex = new MenuEx();
//        ex.setId(item.getId().toString());
//        ex.setValidInd(item.getValidInd());
//        ex.setIcon(item.getIcon());
//        ex.setPid(item.getPid());
//        ex.setDisplay(item.getDisplay());
//        ex.setTitleCode(item.getMenuCode());
//        ex.setTitle(item.getMenuName());
//        ex.setSortField(item.getSortField());
//        return ex;
//    }
}
