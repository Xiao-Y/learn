package com.billow.system.service.impl;

import com.billow.system.dao.MenuDao;
import com.billow.system.dao.RoleMenuDao;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.properties.CustomProperties;
import com.billow.system.service.MenuService;
import com.billow.system.service.redis.MenuRedisKit;
import com.billow.system.service.redis.RoleMenuRedisKit;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author liuyongtao
 * @create 2018-05-26 9:54
 */
@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RoleMenuRedisKit roleMenuRedisKit;
    @Autowired
    private CustomProperties customProperties;
    @Autowired
    private MenuRedisKit menuRedisKit;

    @Override
    public List<MenuEx> homeMenus(MenuVo menuVo) {
        // 查询出该用户所有角色的所有的菜单
        List<RoleVo> roleVos = menuVo.getRoleVos();
        if (ToolsUtils.isEmpty(roleVos)) {
            return null;
        }
        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();

        // 先从 redis 中查询
        if (customProperties.getMenu().isReadCache()) {
            pMenuExs = roleMenuRedisKit.findMenusByRoles(roleVos);
            if (ToolsUtils.isNotEmpty(pMenuExs)) {
                return pMenuExs;
            }
        }

        Set<Long> menuIds = new HashSet<>();
        if (ToolsUtils.isNotEmpty(roleVos)) {
            roleVos.forEach(item -> {
                List<RoleMenuPo> roleMenuPos = roleMenuDao.findByRoleIdIsAndValidIndIsTrue(item.getId());
                if (ToolsUtils.isNotEmpty(roleMenuPos)) {
                    roleMenuPos.stream().forEach(roleMenuPo -> menuIds.add(roleMenuPo.getMenuId()));
                }
            });
        }
//        // TODO 测试用
//        if ("admin".equals(menuVo.getUserCode())) {
//            List<MenuPo> all = menuDao.findAll();
//            if (ToolsUtils.isNotEmpty(all)) {
//                Set<Long> set = all.stream()
//                        .filter(f -> f.getValidInd())
//                        .map(m -> m.getId())
//                        .collect(Collectors.toSet());
//                menuIds.clear();
//                menuIds.addAll(set);
//            }
//        }
        // 如果没有权限直接返回
        if (ToolsUtils.isEmpty(menuIds)) {
            return null;
        }
        // 查询父级菜单
        List<MenuPo> menuPos = menuDao.findByPidIsNullAndValidIndIsTrue();

        if (ToolsUtils.isNotEmpty(menuPos)) {
            this.menuPosCoverMenuExs(menuPos, pMenuExs, menuIds);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, menuIds);
        }
        return pMenuExs;
    }

    @Override
    public List<MenuEx> findMenus() {

        List<MenuEx> menusTree = menuRedisKit.getMenusTree();
        if (CollectionUtils.isNotEmpty(menusTree)) {
            return menusTree;
        }
        // 查询父级菜单
        List<MenuPo> menuPos = menuDao.findByPidIsNull();
        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(menuPos)) {
            this.menuPosCoverMenuExs(menuPos, pMenuExs, null);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, null);
        }
        return menuRedisKit.setMenusTree(pMenuExs);
    }

    @Override
    public MenuVo findMenuById(Long id) {
        MenuVo menuById = menuRedisKit.getMenuById(id);
        if (menuById != null) {
            return menuById;
        }
        Optional<MenuPo> menuPo = menuDao.findById(id);
        return menuRedisKit.setMenuById(ConvertUtils.convert(menuPo.orElse(new MenuPo()), MenuVo.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MenuVo saveOrUpdateMenu(MenuVo menuVo) throws Exception {
        Long id = menuVo.getId();
        MenuPo one;
        if (null != id) {
            one = menuDao.findById(id).get();
            ConvertUtils.copyNonNullProperties(menuVo, one);
        } else {
            menuVo.setValidInd(true);
            one = ConvertUtils.convert(menuVo, MenuPo.class);
        }
        // 保存到数据库
        menuDao.save(one);
        // 更新缓存
        if (null != id) {
            roleMenuRedisKit.updateMeunById(one);
            menuVo = menuRedisKit.updateMenuById(ConvertUtils.convert(one, MenuVo.class));
        } else {
            menuVo = menuRedisKit.setMenuById(ConvertUtils.convert(one, MenuVo.class));
        }
        menuRedisKit.delMenusTree();
        return menuVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMenuByIds(Set<String> ids) {
        ids.forEach(id -> {
            List<RoleMenuPo> roleMenuPos = roleMenuDao.findByMenuId(new Long(id));
            if (ToolsUtils.isNotEmpty(roleMenuPos)) {
                roleMenuDao.deleteByMenuId(new Long(id));
            }

            Optional<MenuPo> menuPo = menuDao.findById(new Long(id));
            if (menuPo.isPresent()) {
                menuDao.deleteById(new Long(id));
            }
        });
        roleMenuRedisKit.deleteRoleMenuByIds(ids);
        menuRedisKit.delMenuByIds(ids);
        menuRedisKit.delMenusTree();
    }

    @Override
    public List<MenuEx> findMenuByRole(RolePo rolePo) {
        List<RoleMenuPo> roleMenuPos = roleMenuDao.findByRoleIdIsAndValidIndIsTrue(rolePo.getId());
        List<MenuEx> menuPos = roleMenuPos.stream().map(m -> {
            MenuPo menuPo = menuDao.findById(m.getMenuId()).get();
            return roleMenuRedisKit.menuPoCoverMenuex(menuPo);
        }).collect(Collectors.toList());
        return menuPos;
    }

    @Override
    public Integer countMenuCodeByMenuCode(String menuCode) {
        return menuDao.countByMenuCodeIsAndValidIndIsTrue(menuCode);
    }

    @Override
    public Set<String> getParentByCurrentId(Long id) {
        Set<String> set = new HashSet<>();
        MenuPo one = menuDao.getOne(id);
        if (one.getPid() != null) {
            this.getMenuPidById(set, one.getPid());
        }
        return set;
    }

    /**
     * 递归查询父级菜单id
     *
     * @param set
     * @param id
     * @return void
     * @author LiuYongTao
     * @date 2019/8/2 10:57
     */
    private void getMenuPidById(Set<String> set, Long id) {
        MenuPo one = menuDao.getOne(id);
        if (one.getPid() != null) {
            this.getMenuPidById(set, one.getPid());
        }
        set.add(one.getId().toString());
    }

    /**
     * 递归查询子级菜单
     *
     * @param pMenuExs
     * @param menuIds  用户所拥有的权限
     * @return void
     * @author LiuYongTao
     * @date 2018/5/29 14:48
     */
    private void childenMenus(List<MenuEx> pMenuExs, Set<Long> menuIds) {
        pMenuExs.forEach(pitem -> {
            // 查询子级菜单
            List<MenuPo> menuPos;
            if (pitem.getValidInd() != null && pitem.getValidInd()) {
                menuPos = menuDao.findByPidEqualsAndValidIndIsTrue(new Long(pitem.getId()));
            } else {
                menuPos = menuDao.findByPidEquals(new Long(pitem.getId()));
            }

            if (ToolsUtils.isNotEmpty(menuPos)) {
                // 转换子级菜单
                List<MenuEx> cMenuExs = new ArrayList<>();
                this.menuPosCoverMenuExs(menuPos, cMenuExs, menuIds);
                // 过滤出是子菜单的数据
                menuPos.stream().filter(f -> f.getDisplay()).forEach(f -> pitem.setIsChildrenDisplay(true));
                cMenuExs.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
                pitem.setChildren(cMenuExs);
                // 递归查询子级菜单
                this.childenMenus(cMenuExs, menuIds);
            }
        });
    }

    /**
     * MenuPos 转换成 MenuExs
     *
     * @param menuPos
     * @param pMenuExs
     * @param menuIds  用户所拥有的权限
     * @return void
     * @author LiuYongTao
     * @date 2018/5/29 14:55
     */
    private void menuPosCoverMenuExs(List<MenuPo> menuPos, List<MenuEx> pMenuExs, Set<Long> menuIds) {
        menuPos.forEach(item -> {
            // 如果没有权限直接抛弃
            if (menuIds != null && !menuIds.contains(item.getId())) {
                return;
            }
            MenuEx ex = roleMenuRedisKit.menuPoCoverMenuex(item);
            pMenuExs.add(ex);
        });
        pMenuExs.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
    }
}
