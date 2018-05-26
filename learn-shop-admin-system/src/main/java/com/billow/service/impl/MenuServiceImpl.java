package com.billow.service.impl;

import com.billow.dao.PermissionDao;
import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.po.sys.PermissionPo;
import com.billow.pojo.vo.sys.PermissionVo;
import com.billow.service.MenuService;
import com.billow.tools.utlis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务
 *
 * @author liuyongtao
 * @create 2018-05-26 9:54
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<MenuEx> indexMenus() {
        List<PermissionPo> pPermissionPos = permissionDao.findByPidIsNull();
        List<MenuEx> pMenuExs = PageUtil.convert(pPermissionPos, MenuEx.class);
        pMenuExs.forEach(pMenuEx -> {
            List<PermissionPo> cPermissionPos = permissionDao.findByPidEquals(pMenuEx.getId());
            List<MenuEx> cMenuExs = PageUtil.convert(cPermissionPos, MenuEx.class);
            pMenuEx.setcMenus(cMenuExs);
        });
        return pMenuExs;
    }


}
