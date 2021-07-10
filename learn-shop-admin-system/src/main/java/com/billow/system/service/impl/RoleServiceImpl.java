package com.billow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.MenuDao;
import com.billow.system.dao.PermissionDao;
import com.billow.system.dao.RoleDao;
import com.billow.system.dao.RoleMenuDao;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.ex.DataDictionaryEx;
import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.system.service.RoleMenuService;
import com.billow.system.service.RolePermissionService;
import com.billow.system.service.RoleService;
import com.billow.system.service.redis.RoleMenuRedisKit;
import com.billow.system.service.redis.RolePermissionRedisKit;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.MathUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色信息
 *
 * @author liuyongtao
 * @create 2018-11-05 16:16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RolePo> implements RoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RolePermissionRedisKit rolePermissionRedisKit;
    @Autowired
    private RoleMenuRedisKit roleMenuRedisKit;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<RoleVo> findRoleListInfoByUserId(Long userId) {
        List<RoleVo> roleVos = new ArrayList<>();
        LambdaQueryWrapper<UserRolePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserRolePo::getUserId, userId);
        List<UserRolePo> userRolePos = userRoleDao.selectList(wrapper);
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            userRolePos.stream().forEach(userRolePo -> {
                RolePo rolePo = roleDao.selectById(userRolePo.getRoleId());
                RoleVo roleVo = ConvertUtils.convert(rolePo, RoleVo.class);
                roleVos.add(roleVo);
            });
        }
        return roleVos;
    }

    @Override
    public IPage<RolePo> findRoleByCondition(RoleVo roleVo) throws Exception {
        IPage<RolePo> page = new Page<>(roleVo.getPageNo(), roleVo.getPageSize());
        LambdaQueryWrapper<RolePo> condition = Wrappers.lambdaQuery();
        condition.eq(StringUtils.isNotEmpty(roleVo.getRoleCode()), RolePo::getRoleCode, roleVo.getRoleCode())
                .like(StringUtils.isNotEmpty(roleVo.getRoleName()), RolePo::getRoleName, roleVo.getRoleName());
        IPage<RolePo> rolePos = roleDao.selectPage(page, condition);
        return rolePos;
    }

    @Override
    public List<Long> findPermissionByRoleId(Long roleId) throws Exception {
        // 查询权限信息
        LambdaQueryWrapper<RolePermissionPo> condition = Wrappers.lambdaQuery();
        condition.eq(RolePermissionPo::getRoleId, roleId)
                .eq(RolePermissionPo::getValidInd, true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.selectList(condition);
        return rolePermissionPos.stream().map(m -> m.getPermissionId()).collect(Collectors.toList());
    }

    @Override
    public List<String> findMenuByRoleId(Long roleId) throws Exception {
        Set<String> delMenus = new HashSet<>();
        LambdaQueryWrapper<RoleMenuPo> condition = Wrappers.lambdaQuery();
        condition.eq(RoleMenuPo::getRoleId, roleId)
                .eq(RoleMenuPo::getValidInd, true);
        List<RoleMenuPo> roleMenuPos = roleMenuDao.selectList(condition);
        // 所有选种的菜单
        List<String> collect = roleMenuPos.stream()
                .map(m -> m.getMenuId().toString())
                .collect(Collectors.toList());
        // 异常：防止勾选父级菜单后，又再其下添加新菜单，这样导致新添加的菜单自动勾选上。
        // 处理：如果勾选了父级菜单但是该角色又没有其下的子菜单，就移除该所有直接父级菜单
        Iterator<String> iterator = collect.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            // 查询子级菜单
            LambdaQueryWrapper<MenuPo> condition1 = Wrappers.lambdaQuery();
            condition1.eq(MenuPo::getPid, new Long(next));
            List<MenuPo> chiledMenus = menuDao.selectList(condition1);
            if (ToolsUtils.isNotEmpty(chiledMenus)) {
                List<String> childeIds = chiledMenus.stream().map(m -> m.getId().toString()).collect(Collectors.toList());
                // 比较所有选种的菜单中是否包含子级菜单
                List<String> intersection = MathUtils.getIntersection(collect, childeIds);
                // 如果该菜单下有子菜单，但是没有勾选，所有移除该父菜单
                if (ToolsUtils.isEmpty(intersection)) {
                    delMenus.add(next);
                    // 查询出当前菜单的所有父级菜单，准备移除
                    Set<String> set = menuService.getParentByCurrentId(new Long(next));
                    if (ToolsUtils.isNotEmpty(set)) {
                        delMenus.addAll(set);
                    }
                }
            }
        }
        // 移除菜单
        if (ToolsUtils.isNotEmpty(delMenus)) {
            collect.removeAll(delMenus);
        }
        return collect;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveRole(RoleVo roleVo) {
        // 保存/修改角色信息
        RolePo rolePo = ConvertUtils.convert(roleVo, RolePo.class);
        Long id = rolePo.getId();
        if (id != null) {
            // 表示是新添加的角色
            roleVo.setIsNewRole(false);
            RolePo one = roleDao.selectById(id);
            // 如果是无效状态时，删除redis 中的信息
            if (!rolePo.getValidInd()) {
                rolePermissionRedisKit.deleteRoleByRoleCode(one.getRoleCode());
                roleMenuRedisKit.deleteRoleByRoleCode(one.getRoleCode());
            } else {
                // 更新角色CODE
                rolePermissionRedisKit.updateRoleCode(roleVo.getRoleCode(), one.getRoleCode());
                roleMenuRedisKit.updateRoleCode(roleVo.getRoleCode(), one.getRoleCode());
            }
        }
        this.saveOrUpdate(rolePo);
        ConvertUtils.convert(rolePo, roleVo);
        // 保存、更新数据库和 redis 中的角色权限信息
        this.saveOrUpdateRolePerssion(roleVo);
        // 保存、更新数据库和 redis 中的角色菜单信息
        this.saveOrUpdateRoleMenu(roleVo);
    }

    /**
     * 保存、更新数据库和 redis 中的角色菜单信息
     *
     * @param roleVo
     * @return void
     * @author billow
     * @date 2019/8/31 10:48
     */
    private void saveOrUpdateRoleMenu(RoleVo roleVo) {
        // 需要删除的
        List<Long> delMenuIds = new ArrayList<>();
        // 页面传过来选种的菜单
        List<String> menuChecked = roleVo.getMenuChecked();
        // 原始选种的菜单
        List<String> oldMenuChecked = roleVo.getOldMenuChecked();
        // 不是新添加的用户
        if (!roleVo.getIsNewRole()) {
            // 分析需要删除
            delMenuIds = oldMenuChecked.stream()
                    .filter(f -> !roleVo.getMenuChecked().contains(f))
                    .map(m -> new Long(m))
                    .collect(Collectors.toList());
            // 删除原始的关联菜单数据
            if (CollectionUtils.isNotEmpty(delMenuIds)) {
                LambdaQueryWrapper<RoleMenuPo> condition = Wrappers.lambdaQuery();
                condition.eq(RoleMenuPo::getRoleId, roleVo.getId())
                        .in(RoleMenuPo::getMenuId, delMenuIds);
                roleMenuDao.delete(condition);
            }
            // 分析需要插入新的
            menuChecked = menuChecked.stream().filter(f -> !oldMenuChecked.contains(f)).collect(Collectors.toList());
        }

        // 用于更新 redis 中的角色对应的菜单信息
        List<MenuPo> newMenuPos = new ArrayList<>();
        // 保存/修改菜单信息
        List<RoleMenuPo> roleMenuPos = menuChecked.stream().map(m -> {
            RoleMenuPo roleMenuPo = new RoleMenuPo();
            roleMenuPo.setRoleId(roleVo.getId());
            roleMenuPo.setMenuId(new Long(m));
            roleMenuPo.setValidInd(true);
            if (roleVo.getValidInd()) {
                newMenuPos.add(menuDao.selectById(new Long(m)));
            }
            return roleMenuPo;
        }).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuPos);

        if (roleVo.getValidInd()) {
            List<MenuPo> sourceMenuPo = null;
            // 原始的
            List<MenuEx> menuExs = redisUtils.getHash(RedisCst.ROLE_MENU_KEY, roleVo.getRoleCode());
            if (ToolsUtils.isNotEmpty(menuExs)) {
                List<Long> delMenuIdsTemp = delMenuIds;
                sourceMenuPo = menuExs.stream().filter(f -> !delMenuIdsTemp.contains(f))
                        .map(m -> {
                            MenuPo po = ConvertUtils.convert(m, MenuPo.class);
                            po.setMenuCode(m.getTitleCode());
                            po.setMenuName(m.getTitle());
                            po.setId(new Long(m.getId()));
                            return po;
                        }).collect(Collectors.toList());
            }

            if (ToolsUtils.isNotEmpty(sourceMenuPo)) {
                newMenuPos.addAll(sourceMenuPo);
                Map<Long, MenuPo> temp = new HashMap<>();
                for (MenuPo newMenuPo : newMenuPos) {
                    temp.put(newMenuPo.getId(), newMenuPo);
                }
                newMenuPos.clear();
                newMenuPos.addAll(temp.values());
            }
            // 更新指定角色的菜单信息
            roleMenuRedisKit.updateRoleMenuByRoleCode(newMenuPos, roleVo.getRoleCode());
        } else {
            // 清除该角色的菜单信息
            roleMenuRedisKit.deleteRoleByRoleCode(roleVo.getRoleCode());
        }
    }

    /**
     * 保存、更新数据库和 redis 中的角色权限信息
     *
     * @param roleVo
     * @return void
     * @author billow
     * @date 2019/8/31 10:49
     */
    private void saveOrUpdateRolePerssion(RoleVo roleVo) {
        // 需要删除的
        List<Long> delPermissionIds = new ArrayList<>();
        // 页面传过来选种的权限
        List<Long> permissionChecked = roleVo.getPermissionChecked();
        // 原始选种的权限
        List<Long> oldPermissionChecked = roleVo.getOldPermissionChecked();
        // 不是新添加的用户
        if (!roleVo.getIsNewRole()) {
            // 分析需要删除
            delPermissionIds = oldPermissionChecked.stream()
                    .filter(f -> !roleVo.getPermissionChecked().contains(f))
                    .collect(Collectors.toList());
            // 删除原始的关联权限数据
            if (ToolsUtils.isNotEmpty(delPermissionIds)) {
                LambdaQueryWrapper<RolePermissionPo> condition = Wrappers.lambdaQuery();
                condition.eq(RolePermissionPo::getRoleId, roleVo.getId())
                        .in(RolePermissionPo::getPermissionId, delPermissionIds);
                rolePermissionDao.delete(condition);
            }
            // 分析需要插入新的
            permissionChecked = permissionChecked.stream().filter(f -> !oldPermissionChecked.contains(f))
                    .collect(Collectors.toList());
        }

        // 用于更新 redis 中的角色对应的权限信息
        List<PermissionPo> newPermissionPos = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(permissionChecked)) {
            // 保存/修改权限信息
            List<RolePermissionPo> rolePermissionPos = permissionChecked.stream().map(m -> {
                RolePermissionPo rolePermissionPo = new RolePermissionPo();
                rolePermissionPo.setRoleId(roleVo.getId());
                rolePermissionPo.setPermissionId(new Long(m));
                rolePermissionPo.setValidInd(true);
                if (roleVo.getValidInd()) {
                    // 查询出该角色要更新的权限
                    newPermissionPos.add(permissionDao.selectById(new Long(m)));
                }
                return rolePermissionPo;
            }).collect(Collectors.toList());
            rolePermissionService.saveBatch(rolePermissionPos);
        }

        // 如果角色是有效状态时，更新reids 中的信息
        if (roleVo.getValidInd()) {
            List<PermissionPo> sourcePermissionPo = null;
            List<PermissionVo> permissionVos = rolePermissionRedisKit.getRolePermissionByRoleCode(roleVo.getRoleCode());
//            List<PermissionPo> permissionPos = redisUtils.getList(RedisCst.ROLE_PERMISSION_KEY + roleVo.getRoleCode());
            if (ToolsUtils.isNotEmpty(permissionVos)) {
                List<Long> delPermissionIdsTemp = delPermissionIds;
                // 过滤出本来就存在的权限（扫除掉需要删除的）
                sourcePermissionPo = permissionVos.stream()
                        .filter(f -> !delPermissionIdsTemp.contains(f.getId()))
                        .map(m -> ConvertUtils.convertIgnoreBase(m, PermissionPo.class))
                        .collect(Collectors.toList());
            }
            if (ToolsUtils.isNotEmpty(sourcePermissionPo)) {
                newPermissionPos.addAll(sourcePermissionPo);
                Map<Long, PermissionPo> temp = new HashMap<>();
                for (PermissionPo po : newPermissionPos) {
                    temp.put(po.getId(), po);
                }
                newPermissionPos.clear();
                newPermissionPos.addAll(temp.values());
            }
            // 更新指定角色的权限信息
            rolePermissionRedisKit.updateRolePermissionByRoleCode(newPermissionPos, roleVo.getRoleCode());
        } else {
            // 清除该角色的权限信息
            rolePermissionRedisKit.deleteRoleByRoleCode(roleVo.getRoleCode());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVo prohibitRoleById(Long roleId) {
        RolePo one = roleDao.selectById(roleId);
        // 如果不存在，直接构建一个新的返回
        if (one == null) {
            RoleVo roleVo = new RoleVo();
            roleVo.setId(roleId);
            roleVo.setValidInd(false);
            // 删除 redis 信息
//            commonRolePermissionRedis.deleteRoleByRoleCode(one.getRoleCode());
            // 删除 redis 信息
//            commonRoleMenuRedis.deleteRoleByRoleCode(one.getRoleCode());
            return roleVo;
        }
        // 更新角色为无效
        one.setValidInd(false);
        roleDao.updateById(one);

        // 更新该角色的权限为无效
        LambdaQueryWrapper<RolePermissionPo> condition = Wrappers.lambdaQuery();
        condition.eq(RolePermissionPo::getRoleId, roleId)
                .in(RolePermissionPo::getValidInd, true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.selectList(condition);
        for (RolePermissionPo rolePermissionPo : rolePermissionPos) {
            rolePermissionPo.setValidInd(false);
        }
        rolePermissionService.saveBatch(rolePermissionPos);
        // 删除 redis 信息
        rolePermissionRedisKit.deleteRoleByRoleCode(one.getRoleCode());

        // 更新该角色的菜单为无效
        LambdaQueryWrapper<RoleMenuPo> condition1 = Wrappers.lambdaQuery();
        condition1.eq(RoleMenuPo::getRoleId, roleId)
                .in(RoleMenuPo::getValidInd, true);
        List<RoleMenuPo> roleMenuPos = roleMenuDao.selectList(condition1);
        for (RoleMenuPo roleMenuPo : roleMenuPos) {
            roleMenuPo.setValidInd(false);
        }
        roleMenuService.saveBatch(roleMenuPos);
        // 删除 redis 信息
        roleMenuRedisKit.deleteRoleByRoleCode(one.getRoleCode());


        return ConvertUtils.convert(one, RoleVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVo deleteRoleById(Long roleId) {
        RolePo one = roleDao.selectById(roleId);
        // 如果不存在，直接构建一个新的返回
        if (one == null) {
            RoleVo roleVo = new RoleVo();
            roleVo.setId(roleId);
            roleVo.setValidInd(false);
            // 删除 redis 信息
//            commonRolePermissionRedis.deleteRoleByRoleCode(one.getRoleCode());
            // 删除 redis 信息
//            commonRoleMenuRedis.deleteRoleByRoleCode(one.getRoleCode());
            return roleVo;
        }
        // 删除角色
        roleDao.deleteById(one);

        // 删除该角色的权限
        LambdaQueryWrapper<RolePermissionPo> condition1 = Wrappers.lambdaQuery();
        condition1.eq(RolePermissionPo::getRoleId, roleId);
        rolePermissionDao.delete(condition1);
        // 删除 redis 信息
        rolePermissionRedisKit.deleteRoleByRoleCode(one.getRoleCode());

        // 删除该角色的菜单
        LambdaQueryWrapper<RoleMenuPo> condition2 = Wrappers.lambdaQuery();
        condition2.eq(RoleMenuPo::getRoleId, roleId);
        roleMenuDao.delete(condition2);
        // 删除 redis 信息
        roleMenuRedisKit.deleteRoleByRoleCode(one.getRoleCode());

        return ConvertUtils.convert(one, RoleVo.class);
    }

    @Override
    public List<DataDictionaryEx> findSelectRole() {
        List<RolePo> rolePos = this.list();
        List<DataDictionaryEx> collect = rolePos.stream().map(m ->
                new DataDictionaryEx(m.getId(), m.getRoleName() + "-" + m.getRoleCode(), m.getId()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<RoleVo> findRoleById(List<Long> ids) {
        List<RolePo> pos = roleDao.selectBatchIds(ids);
        return ConvertUtils.convertIgnoreBase(pos, RoleVo.class);
    }

    @Override
    public Integer countRoleCodeByRoleCode(String roleCode) {
        LambdaQueryWrapper<RolePo> condition2 = Wrappers.lambdaQuery();
        condition2.eq(RolePo::getRoleCode, roleCode);
        return roleDao.selectCount(condition2);
    }

}
