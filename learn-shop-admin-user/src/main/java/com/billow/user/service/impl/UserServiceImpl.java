package com.billow.user.service.impl;

import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.user.dao.PermissionDao;
import com.billow.user.dao.RolePermissionDao;
import com.billow.user.dao.UserDao;
import com.billow.user.dao.UserRoleDao;
import com.billow.user.pojo.po.PermissionPo;
import com.billow.user.pojo.po.RolePermissionPo;
import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.po.UserRolePo;
import com.billow.user.pojo.vo.UserVo;
import com.billow.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:28
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserVo findUserInfoByUsercode(String userCode) {
        UserPo userPo = userDao.findUserInfoByUsercode(userCode);
        UserVo userVo = ConvertUtils.convert(userPo, UserVo.class);
        return userVo;
    }

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        Assert.notNull(usercode, "usercode 不能为空");
        UserPo userPo = userDao.findUserInfoByUsercode(usercode);
        if (userPo == null) {
            logger.error("用户：{}，不存在！", usercode);
            return null;
        }

        Set<GrantedAuthority> userAuthotities = new HashSet<>();

        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserId(userPo.getId());
        if (ToolsUtils.isEmpty(userRolePos)) {
            logger.error("用户：{}，未分配角色！", usercode);
        } else {
            userRolePos.stream().forEach(ur -> {
                List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(ur.getRoleId());
                if (ToolsUtils.isEmpty(rolePermissionPos)) {
                    logger.error("用户：{}，未分配权限！", usercode);
                } else {
                    rolePermissionPos.stream().forEach(rp -> {
                        PermissionPo permissionPo = permissionDao.findOne(rp.getPermissionId());
                        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionPo.getPermissionCode());
                        userAuthotities.add(simpleGrantedAuthority);
                    });
                }
            });
        }
        return new User(userPo.getUsercode(), userPo.getPassword(), userAuthotities);
    }
}
