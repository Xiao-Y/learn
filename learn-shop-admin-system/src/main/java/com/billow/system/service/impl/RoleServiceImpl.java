package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.redis.util.RedisUtils;
import com.billow.system.dao.*;
import com.billow.system.pojo.ex.DataDictionaryEx;
import com.billow.system.pojo.po.*;
import com.billow.system.pojo.search.RolePermissionSearchParam;
import com.billow.system.pojo.search.RoleSearchParam;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.system.service.RoleMenuService;
import com.billow.system.service.RolePermissionService;
import com.billow.system.service.RoleService;
import com.billow.system.service.redis.RoleMenuRedisKit;
import com.billow.system.service.redis.RolePermissionRedisKit;
import com.billow.system.service.redis.RoleRedisKit;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户角色信息
 *
 * @author liuyongtao
 * @create 2018-11-05 16:16
 */
@Slf4j
@Service
public class RoleServiceImpl extends HighLevelServiceImpl<RoleDao, RolePo, RoleSearchParam> implements RoleService {

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
    @Autowired
    private RoleRedisKit roleRedisKit;

    @Override
    public List<RoleVo> findRoleListInfoByUserId(Long userId) {
        List<RoleVo> roleVos = new ArrayList<>();
        UserRolePo userRolePo = new UserRolePo();
        userRolePo.setUserId(userId);
        List<UserRolePo> userRolePos = userRoleDao.queryList(userRolePo);
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            userRolePos.forEach(item -> {
                RolePo rolePo = this.getById(item.getRoleId());
                RoleVo roleVo = ConvertUtils.convert(rolePo, RoleVo.class);
                roleVos.add(roleVo);
            });
        }
        return roleVos;
    }

    @Override
    public List<Long> findPermissionByRoleId(Long roleId) throws Exception {
        // 查询权限信息
        RolePermissionSearchParam rolePermissionSearchParam = new RolePermissionSearchParam();
        rolePermissionSearchParam.setRoleId(roleId);
        rolePermissionSearchParam.setValidInd(true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionService.findList(rolePermissionSearchParam);
        return rolePermissionPos.stream()
                .map(RolePermissionPo::getPermissionId)
                .collect(Collectors.toList());
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
            RolePo one = this.getById(id);
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
        roleRedisKit.updateRoleInfoCache(rolePo.getId());
        ConvertUtils.convert(rolePo, roleVo);
        // 保存、更新数据库和 redis 中的角色权限信息
        this.saveOrUpdateRolePermission(roleVo);
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
        List<String> oldMenuChecked = roleMenuService.queryChain()
                .eq(RoleMenuPo::getRoleId, roleVo.getId())
                .list()
                .stream()
                .map(m -> String.valueOf(m.getMenuId()))
                .collect(Collectors.toList());
        // 不是新添加的用户
        if (!roleVo.getIsNewRole()) {
            // 分析需要删除
            delMenuIds = oldMenuChecked.stream()
                    .filter(f -> !roleVo.getMenuChecked().contains(f))
                    .map(m -> Long.valueOf(m))
                    .collect(Collectors.toList());
            // 删除原始的关联菜单数据
            if (CollectionUtils.isNotEmpty(delMenuIds)) {
                QueryWrapper condition = QueryWrapper.create();
                condition.eq(RoleMenuPo::getRoleId, roleVo.getId())
                        .in(RoleMenuPo::getMenuId, delMenuIds);
                roleMenuDao.deleteByQuery(condition);
            }
            // 分析需要插入新的
            menuChecked = menuChecked.stream()
                    .filter(f -> !oldMenuChecked.contains(f))
                    .collect(Collectors.toList());
        }

        // 用于更新 redis 中的角色对应的菜单信息
        List<MenuPo> newMenuPos = new ArrayList<>();
        // 保存/修改菜单信息
        List<RoleMenuPo> roleMenuPos = menuChecked.stream().map(m -> {
            RoleMenuPo roleMenuPo = new RoleMenuPo();
            roleMenuPo.setRoleId(roleVo.getId());
            roleMenuPo.setMenuId(Long.parseLong(m));
            roleMenuPo.setValidInd(true);
            if (roleVo.getValidInd()) {
                newMenuPos.add(menuDao.selectOneById(Long.parseLong(m)));
            }
            return roleMenuPo;
        }).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuPos);

        if (roleVo.getValidInd()) {
            List<MenuPo> sourceMenuPo = null;
            // 原始的
            List<MenuPo> menuExs = redisUtils.getHash(RedisCst.ROLE_MENU_KEY, roleVo.getRoleCode(), MenuPo.class);
            if (ToolsUtils.isNotEmpty(menuExs)) {
                List<Long> delMenuIdsTemp = delMenuIds;
                sourceMenuPo = menuExs.stream()
                        .filter(f -> !delMenuIdsTemp.contains(f.getId()))
                        .collect(Collectors.toList());
            }

            if (ToolsUtils.isNotEmpty(sourceMenuPo)) {
                newMenuPos.addAll(sourceMenuPo);
                Map<Long, MenuPo> temp = new HashMap<>();
                for (MenuPo newMenuPo : newMenuPos) {
                    if (Objects.isNull(newMenuPo)) {
                        continue;
                    }
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
    private void saveOrUpdateRolePermission(RoleVo roleVo) {
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
                QueryWrapper condition = QueryWrapper.create();
                condition.eq(RolePermissionPo::getRoleId, roleVo.getId())
                        .in(RolePermissionPo::getPermissionId, delPermissionIds);
                rolePermissionDao.deleteByQuery(condition);
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
                rolePermissionPo.setPermissionId(m);
                rolePermissionPo.setValidInd(true);
                if (roleVo.getValidInd()) {
                    // 查询出该角色要更新的权限
                    newPermissionPos.add(permissionDao.selectOneById(m));
                }
                return rolePermissionPo;
            }).collect(Collectors.toList());
            rolePermissionService.saveBatch(rolePermissionPos);
        }

        // 如果角色是有效状态时，更新reids 中的信息
        if (roleVo.getValidInd()) {
            List<PermissionPo> sourcePermissionPo = null;
            List<PermissionVo> permissionVos = rolePermissionRedisKit.getRolePermissionByRoleCode(roleVo.getRoleCode());
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
        RolePo one = this.getById(roleId);
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
        this.updateById(one);

        // 更新该角色的权限为无效
        QueryWrapper condition = QueryWrapper.create();
        condition.eq(RolePermissionPo::getRoleId, roleId)
                .in(RolePermissionPo::getValidInd, true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.selectListByQuery(condition);
        for (RolePermissionPo rolePermissionPo : rolePermissionPos) {
            rolePermissionPo.setValidInd(false);
        }
        rolePermissionService.saveBatch(rolePermissionPos);
        // 删除 redis 信息
        rolePermissionRedisKit.deleteRoleByRoleCode(one.getRoleCode());

        // 更新该角色的菜单为无效
        QueryWrapper condition1 = QueryWrapper.create();
        condition1.eq(RoleMenuPo::getRoleId, roleId)
                .in(RoleMenuPo::getValidInd, true);
        List<RoleMenuPo> roleMenuPos = roleMenuDao.selectListByQuery(condition1);
        for (RoleMenuPo roleMenuPo : roleMenuPos) {
            roleMenuPo.setValidInd(false);
        }
        roleMenuService.saveBatch(roleMenuPos);
        // 删除 redis 信息
        roleMenuRedisKit.deleteRoleByRoleCode(one.getRoleCode());
        roleRedisKit.deleteRoleById(one.getId());

        return ConvertUtils.convert(one, RoleVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVo deleteRoleById(Long roleId) {
        RolePo one = this.getById(roleId);
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
        QueryWrapper condition1 = QueryWrapper.create();
        condition1.eq(RolePermissionPo::getRoleId, roleId);
        rolePermissionDao.deleteByQuery(condition1);
        // 删除 redis 信息
        rolePermissionRedisKit.deleteRoleByRoleCode(one.getRoleCode());

        // 删除该角色的菜单
        QueryWrapper condition2 = QueryWrapper.create();
        condition2.eq(RoleMenuPo::getRoleId, roleId);
        roleMenuDao.deleteByQuery(condition2);
        // 删除 redis 信息
        roleMenuRedisKit.deleteRoleByRoleCode(one.getRoleCode());
        roleRedisKit.deleteRoleById(one.getId());
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
        List<RolePo> pos = roleDao.selectListByIds(ids);
        return ConvertUtils.convertIgnoreBase(pos, RoleVo.class);
    }

    @Override
    public Long countRoleCodeByRoleCode(String roleCode) {
        QueryWrapper condition2 = QueryWrapper.create();
        condition2.eq(RolePo::getRoleCode, roleCode);
        return this.count(condition2);
    }

    @Override
    public List<String> findChildrenMenuByRoleId(Long roleId) {
        return roleMenuDao.findChildrenMenuByRoleId(roleId);
    }

}
