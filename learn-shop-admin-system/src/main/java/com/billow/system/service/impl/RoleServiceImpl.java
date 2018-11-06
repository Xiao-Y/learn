package com.billow.system.service.impl;

import com.billow.system.dao.RoleDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.RoleService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色信息
 *
 * @author liuyongtao
 * @create 2018-11-05 16:16
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<RoleVo> findRoleListInfoByUserId(Long userId) {
        List<RoleVo> roleVos = new ArrayList<>();
        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserId(userId);
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            userRolePos.stream().forEach(userRolePo -> {
                RolePo rolePo = roleDao.findOne(userRolePo.getRoleId());
                RoleVo roleVo = ConvertUtils.convert(rolePo, RoleVo.class);
                roleVos.add(roleVo);
            });
        }
        return roleVos;
    }
}
