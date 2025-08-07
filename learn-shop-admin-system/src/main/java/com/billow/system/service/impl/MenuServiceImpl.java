package com.billow.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.system.dao.MenuDao;
import com.billow.system.dao.RoleMenuDao;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.system.service.redis.MenuRedisKit;
import com.billow.system.service.redis.RoleMenuRedisKit;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜单服务
 *
 * @author liuyongtao
 * @create 2018-05-26 9:54
 */
@Slf4j
@Service
@RefreshScope
@Transactional(readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuPo> implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RoleMenuRedisKit roleMenuRedisKit;
    @Autowired
    private MenuRedisKit menuRedisKit;

    @Override
    public List<Tree<Long>> findMenus() {
        // 查询缓存，如果不存在，查询DB
        List<MenuPo> menuPos = menuRedisKit.getMenusList();
        if (CollectionUtils.isEmpty(menuPos)) {
            menuPos = this.list();
            menuRedisKit.setMenusList(menuPos);
        }
        // 构建菜单树
        List<Tree<Long>> treeNodes = this.genMenuTrees(menuPos);
        return treeNodes;
    }

    @Override
    public MenuVo findMenuById(Long id) {
        MenuVo menuById = menuRedisKit.getMenuById(id);
        if (menuById != null) {
            return menuById;
        }
        MenuPo menuPo = menuDao.selectById(id);
        return menuRedisKit.setMenuById(ConvertUtils.convert(menuPo, MenuVo.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MenuVo saveOrUpdateMenu(MenuVo menuVo) throws Exception {
        Long id = menuVo.getId();
        MenuPo one;
        if (null != id) {
            one = menuDao.selectById(id);
            ConvertUtils.copyNonNullProperties(menuVo, one);
        } else {
            menuVo.setValidInd(true);
            one = ConvertUtils.convert(menuVo, MenuPo.class);
        }
        // 保存到数据库
        this.saveOrUpdate(one);
        // 更新缓存
        if (null != id) {
            roleMenuRedisKit.updateMenuById(one);
            menuVo = menuRedisKit.updateMenuById(ConvertUtils.convert(one, MenuVo.class));
        } else {
            menuVo = menuRedisKit.setMenuById(ConvertUtils.convert(one, MenuVo.class));
        }
        return menuVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMenuByIds(Set<String> ids) {
        ids.forEach(id -> {
            LambdaQueryWrapper<RoleMenuPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(RoleMenuPo::getMenuId, new Long(id));
            List<RoleMenuPo> roleMenuPos = roleMenuDao.selectList(wrapper);
            if (ToolsUtils.isNotEmpty(roleMenuPos)) {
                wrapper = Wrappers.lambdaQuery();
                wrapper.eq(RoleMenuPo::getMenuId, new Long(id));
                roleMenuDao.delete(wrapper);
            }

            MenuPo menuPo = menuDao.selectById(new Long(id));
            if (menuPo != null) {
                menuDao.deleteById(new Long(id));
            }
        });
        roleMenuRedisKit.deleteRoleMenuByIds(ids);
        menuRedisKit.delMenuByIds(ids);
    }

    @Override
    public List<MenuPo> findMenuByRole(RolePo rolePo) {
        return menuDao.findMenuByRoleCode(Arrays.asList(rolePo.getRoleCode()), false);
//        this.findMenuByRoleCode(Arrays.asList(rolePo.getRoleCode()), false);
//        LambdaQueryWrapper<RoleMenuPo> wrapper = Wrappers.lambdaQuery();
//        wrapper.eq(RoleMenuPo::getRoleId, rolePo.getId())
//                .eq(RoleMenuPo::getValidInd, true);
//        List<RoleMenuPo> roleMenuPos = roleMenuDao.selectList(wrapper);
//        Set<MenuEx> menuPos = roleMenuPos.stream().map(m -> {
//            MenuPo menuPo = menuDao.selectById(m.getMenuId());
//            return roleMenuRedisKit.menuPoCoverMenuex(menuPo);
//        }).collect(Collectors.toSet());
//        return new ArrayList<>(menuPos);
    }

    @Override
    public Long countMenuCodeByMenuCode(String menuCode) {
        LambdaQueryWrapper<MenuPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuPo::getMenuCode, menuCode)
                .eq(MenuPo::getValidInd, true);
        return this.count(wrapper);
    }

    @Override
    public Set<String> getParentByCurrentId(Long id) {
        Set<String> set = new HashSet<>();
        MenuPo one = menuDao.selectById(id);
        if (one.getPid() != null) {
            this.getMenuPidById(set, one.getPid());
        }
        return set;
    }

    @Override
    public List<MenuPo> findRouterList(List<RoleVo> roleVos) {
        if (CollectionUtils.isEmpty(roleVos)) {
            return new ArrayList<>();
        }
        return roleMenuRedisKit.findMenuListByRoles(roleVos);
    }

    @Override
    public List<Tree<Long>> findMenuByRoleCode(List<String> roleCodes, boolean isDisplay) {
        List<MenuPo> menuPos = menuDao.findMenuByRoleCode(roleCodes, isDisplay);
        List<Tree<Long>> treeNodes = this.genMenuTrees(menuPos);
        return treeNodes;
    }

    @Override
    public List<MenuVo> findMenuByPermissionId(Long permissionId) {
        List<MenuPo> menuPos = this.baseMapper.findMenuByPermissionId(permissionId);
        return BeanUtil.copyToList(menuPos, MenuVo.class);
    }

    /**
     * 构建菜单树
     *
     * @param menuPos
     * @return List<Tree < Long>>
     * @author 千面
     * @date 2022/1/5 10:18
     */
    private List<Tree<Long>> genMenuTrees(List<MenuPo> menuPos) {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义返回属性名
        treeNodeConfig.setIdKey("id")
                .setParentIdKey("pid")
                .setNameKey("menuName")
                .setChildrenKey("children")
                .setWeightKey("sortField");

        //转换器
        List<Tree<Long>> treeNodes = TreeUtil.build(menuPos, null, treeNodeConfig,
                (treeNode, tree) -> {
                    // 设置映射值
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getPid());
                    tree.setWeight(treeNode.getSortField());
                    tree.setName(treeNode.getMenuName());
                    // 扩展属性 ...
                    tree.putExtra("display", treeNode.getDisplay());
                    tree.putExtra("icon", treeNode.getIcon());
                    tree.putExtra("menuCode", treeNode.getMenuCode());
                    tree.putExtra("validInd", treeNode.getValidInd());
                });
        return treeNodes;
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
        MenuPo one = menuDao.selectById(id);
        if (one.getPid() != null) {
            this.getMenuPidById(set, one.getPid());
        }
        set.add(one.getId().toString());
    }

    //    /**
//     * 递归查询子级菜单
//     *
//     * @param pMenuExs
//     * @param menuIds  用户所拥有的权限
//     * @return void
//     * @author LiuYongTao
//     * @date 2018/5/29 14:48
//     */
//    private void childenMenus(List<MenuEx> pMenuExs, Set<Long> menuIds)
//    {
//        pMenuExs.forEach(pitem -> {
//            // 查询子级菜单
//            List<MenuPo> menuPos;
//            LambdaQueryWrapper<MenuPo> wrapper = Wrappers.lambdaQuery();
//            wrapper.eq(MenuPo::getPid, new Long(pitem.getId()));
//            if (pitem.getValidInd() != null && pitem.getValidInd())
//            {
//                wrapper.eq(MenuPo::getValidInd, true);
//            }
//            menuPos = menuDao.selectList(wrapper);
//
//            if (ToolsUtils.isNotEmpty(menuPos))
//            {
//                // 转换子级菜单
//                List<MenuEx> cMenuExs = new ArrayList<>();
//                this.menuPosCoverMenuExs(menuPos, cMenuExs, menuIds);
//                // 过滤出是子菜单的数据
//                menuPos.stream().filter(f -> f.getDisplay()).forEach(f -> pitem.setIsChildrenDisplay(true));
//                cMenuExs.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
//                pitem.setChildren(cMenuExs);
//                // 递归查询子级菜单
//                this.childenMenus(cMenuExs, menuIds);
//            }
//        });
//    }
//
//    /**
//     * MenuPos 转换成 MenuExs
//     *
//     * @param menuPos
//     * @param pMenuExs
//     * @param menuIds  用户所拥有的权限
//     * @return void
//     * @author LiuYongTao
//     * @date 2018/5/29 14:55
//     */
//    private void menuPosCoverMenuExs(List<MenuPo> menuPos, List<MenuEx> pMenuExs, Set<Long> menuIds)
//    {
//        menuPos.forEach(item -> {
//            // 如果没有权限直接抛弃
//            if (menuIds != null && !menuIds.contains(item.getId()))
//            {
//                return;
//            }
//            MenuEx ex = roleMenuRedisKit.menuPoCoverMenuex(item);
//            pMenuExs.add(ex);
//        });
//        pMenuExs.sort(Comparator.comparing(MenuEx::getSortField, Comparator.nullsLast(Double::compareTo)));
//    }

}
