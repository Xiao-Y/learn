package com.billow.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.MenuPermissionDao;
import com.billow.system.dao.PermissionDao;
import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.search.MenuPermissionSearchParam;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.service.MenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-26
 */
@Service
public class MenuPermissionServiceImpl extends HighLevelServiceImpl<MenuPermissionDao, MenuPermissionPo, MenuPermissionSearchParam> implements MenuPermissionService
{

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<PermissionVo> findPermissionByMenuId(Long menuId)
    {
        List<PermissionPo> permissionPos = permissionDao.findPermissionByMenuId(menuId);
        return BeanUtil.copyToList(permissionPos, PermissionVo.class);
    }
}

