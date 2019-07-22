package com.billow.system.service.impl;

import com.billow.system.dao.MenuDao;
import com.billow.system.dao.RoleMenuDao;

import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.pojo.vo.MenuVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Override
    public List<MenuEx> homeMenus(MenuVo menuVo) {
        // 查询出该用户所有角色的所有的菜单
        List<RoleVo> roleVos = menuVo.getRoleVos();
        Set<Long> menuIds = new HashSet<>();
        if (ToolsUtils.isNotEmpty(roleVos)) {
            roleVos.forEach(item -> {
                List<RoleMenuPo> roleMenuPos = roleMenuDao.findByRoleIdIsAndValidIndIsTrue(item.getId());
                if (ToolsUtils.isNotEmpty(roleMenuPos)) {
                    roleMenuPos.stream().forEach(roleMenuPo -> menuIds.add(roleMenuPo.getMenuId()));
                }
            });
        }
        // TODO 测试用
        if ("admin".equals(menuVo.getUserCode())) {
            List<MenuPo> all = menuDao.findAll();
            if (ToolsUtils.isNotEmpty(all)) {
                Set<Long> set = all.stream().map(m -> m.getId()).collect(Collectors.toSet());
                menuIds.clear();
                menuIds.addAll(set);
            }
        }
        // 如果没有权限直接返回
        if (ToolsUtils.isEmpty(menuIds)) {
            return null;
        }
        // 查询父级菜单
        List<MenuPo> menuPos = menuDao.findByPidIsNullAndValidIndIsTrue();

        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(menuPos)) {
            this.menuPosCoverMenuExs(menuPos, pMenuExs, menuIds);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, menuIds);
        }
        return pMenuExs;
    }

    @Override
    public List<MenuEx> findMenus() {
        // 查询父级菜单
        List<MenuPo> menuPos = menuDao.findByPidIsNull();

        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(menuPos)) {
            this.menuPosCoverMenuExs(menuPos, pMenuExs, null);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, null);
        }
        return pMenuExs;
    }

    @Override
    public MenuVo findMenuById(Long id) {
        MenuPo menuPo = menuDao.findOne(id);
        return ConvertUtils.convert(menuPo, MenuVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MenuVo saveOrUpdateMenu(MenuVo menuVo) throws Exception {
        Long id = menuVo.getId();
        MenuPo one;
        if (null != id) {
            one = menuDao.findOne(id);
            ConvertUtils.copyNonNullProperties(menuVo, one);
//            one.setUpdateTime(new Date());
        } else {
            one = ConvertUtils.convert(menuVo, MenuPo.class);
            FieldUtils.setCommonFieldByInsertWithValidInd(one, menuVo.getUpdaterCode());
        }
        menuDao.save(one);

        return ConvertUtils.convert(one, MenuVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMenuByIds(Set<String> ids) {
        ids.forEach(id -> {
            List<RoleMenuPo> roleMenuPos = roleMenuDao.findByMenuId(new Long(id));
            if (ToolsUtils.isNotEmpty(roleMenuPos)) {
                roleMenuDao.deleteByMenuId(new Long(id));
            }

            MenuPo menuPo = menuDao.findOne(new Long(id));
            if (null != menuPo) {
                menuDao.delete(new Long(id));
            }
        });
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

            MenuEx ex = new MenuEx();
            ex.setId(item.getId().toString())
                    .setPath("")
                    .setValidInd(item.getValidInd())
                    .setIcon(item.getIcon())
                    .setPid(item.getPid())
                    .setDisplay(item.getDisplay())
                    .setTitleCode(item.getMenuCode())
                    .setTitle(item.getMenuName());
            pMenuExs.add(ex);
        });
    }
}
