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
import com.billow.auth.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    //    @Autowired
//    private RolePermissionDao rolePermissionDao;
//    @Autowired
//    private PermissionDao permissionDao;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.debug("查询用户：{} 的信息...", usercode);
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 查询用户信息
        UserPo userPo = userDao.findUserInfoByUsercode(usercode);
        if (userPo == null) {
            logger.error("用户：{}，不存在！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
        }
        // 查询角色信息
        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserId(userPo.getId());
        if (CollectionUtils.isEmpty(userRolePos)) {
            logger.error("用户：{}，未分配角色！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
        }
        userRolePos.stream().forEach(ur -> {
            Long roleId = ur.getRoleId();
            Optional<RolePo> rolePo = roleDao.findById(roleId);
            if (!rolePo.isPresent()) {
                logger.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
                return;
            }
            Set<PermissionPo> permissionByRole = permissionService.findPermissionByRole(rolePo.get());
            List<SimpleGrantedAuthority> collect = permissionByRole.stream()
                    .map(m -> new SimpleGrantedAuthority(m.getUrl()))
                    .collect(Collectors.toList());
            authorities.addAll(collect);
        });
        if (CollectionUtils.isEmpty(authorities)) {
            logger.error("用户：{}，未分配权限！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配权限");
        }
        return new User(userPo.getUsername(), userPo.getPassword(), authorities);
    }
}
