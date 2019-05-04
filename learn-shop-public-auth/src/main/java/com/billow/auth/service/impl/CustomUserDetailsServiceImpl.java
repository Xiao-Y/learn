package com.billow.auth.service.impl;

import com.billow.auth.dao.PermissionDao;
import com.billow.auth.dao.RoleDao;
import com.billow.auth.dao.RolePermissionDao;
import com.billow.auth.dao.UserDao;
import com.billow.auth.dao.UserRoleDao;
import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePermissionPo;
import com.billow.auth.pojo.po.RolePo;
import com.billow.auth.pojo.po.UserPo;
import com.billow.auth.pojo.po.UserRolePo;
import com.billow.auth.pojo.security.CustomUserDetails;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.debug("查询用户：{} 的信息...",usercode);
        List<RolePo> rolePos = new ArrayList<>();
        List<PermissionPo> permissionPos = new ArrayList<>();
        // 查询用户信息
        UserPo userPo = userDao.findUserInfoByUsercode(usercode);
        if (userPo == null) {
            logger.error("用户：{}，不存在！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
        }
        // 查询角色信息
        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserId(userPo.getId());
        if (ToolsUtils.isEmpty(userRolePos)) {
            logger.error("用户：{}，未分配角色！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
        }
        userRolePos.stream().forEach(ur -> {
            Long roleId = ur.getRoleId();
            RolePo rolePo = roleDao.findOne(roleId);
            if (rolePo == null) {
                logger.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
            } else {
                rolePos.add(rolePo);
            }
            // 查询权限信息
            List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(roleId);
            if (ToolsUtils.isEmpty(rolePermissionPos)) {
                logger.error("用户：{}，角色：{}，未分配权限！", usercode, rolePo.getRoleName());
            } else {
                rolePermissionPos.stream().forEach(rp -> {
                    PermissionPo permissionPo = permissionDao.findOne(rp.getPermissionId());
                    if (permissionPo == null) {
                        logger.error("用户：{}，角色：{}，permissionId:{},未查询到信息！", usercode, rolePo.getRoleName(), permissionPo.getId());
                    } else {
                        permissionPos.add(permissionPo);
                    }
                });
            }
        });
        if (ToolsUtils.isEmpty(permissionPos)) {
            logger.error("用户：{}，未分配权限！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配权限");
        }
        return new CustomUserDetails(userPo, rolePos, permissionPos);
    }

//    @Service
//    public class DomainUserDetailsServiceImpl implements UserDetailsService {
//
//        protected final Logger logger = LoggerFactory.getLogger(getClass());
//
//        @Autowired
//        private UserRemote userRemote;
//
//        @Override
//        public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
//            BaseResponse<UserDetails> baseResponse = userRemote.loadUserByUsername(usercode);
//            UserDetails resData = baseResponse.getResData();
//            Assert.notNull(resData, usercode + "，没有查询到用户信息");
//            return resData;
//        }
//    }
}
