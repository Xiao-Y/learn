package com.billow.auth.service.impl;

import com.billow.auth.dao.PermissionDao;
import com.billow.auth.dao.RolePermissionDao;
import com.billow.auth.dao.UserDao;
import com.billow.auth.dao.UserRoleDao;
import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePermissionPo;
import com.billow.auth.pojo.po.UserPo;
import com.billow.auth.pojo.po.UserRolePo;
import com.billow.auth.remote.UserRemote;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
@Service
public class DomainUserDetailsServiceImpl implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {

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
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("admin");
        userAuthotities.add(simpleGrantedAuthority);
        return new User(userPo.getUsercode(), userPo.getPassword(), userAuthotities);
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
