package com.billow.gateway.security.component;

import com.billow.gateway.dao.RoleDao;
import com.billow.gateway.dao.UserDao;
import com.billow.gateway.dao.UserRoleDao;
import com.billow.gateway.pojo.po.RolePo;
import com.billow.gateway.pojo.po.UserPo;
import com.billow.gateway.pojo.po.UserRolePo;
import com.billow.gateway.pojo.vo.UserRelationVo;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
public class CustomUserDetailsService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.info("查询用户：{} 的信息...", usercode);
        UserRelationVo userRelationVo = this.queryUserRelationByUsercode(usercode);

        // 查询角色信息
        List<RolePo> rolePoList = userRelationVo.getRolePoList();
        if (CollectionUtils.isEmpty(rolePoList)) {
            logger.error("用户：{}，未分配权限！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配权限");
        }
        // 用户信息
        UserPo userPo = userRelationVo.getUserPo();
        // 转换角色信息
        Set<GrantedAuthority> authorities = rolePoList.stream()
                .map(m -> new SimpleGrantedAuthority(m.getRoleCode()))
                .collect(Collectors.toSet());
        return new User(userPo.getUsername(), userPo.getPassword(), authorities);
    }

    public @NotNull UserRelationVo queryUserRelationByUsercode(String usercode) {
        if (ToolsUtils.isEmpty(usercode)) {
            logger.error("用户名不能为空！");
            throw new UsernameNotFoundException("用户名不能为空！");
        }
        UserRelationVo userRelationVo = new UserRelationVo();

        // 查询用户信息
        UserPo userPo = userDao.findUserInfoByUsercode(usercode);
        if (userPo == null) {
            logger.error("用户：{}，不存在！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
        }
        if (!userPo.getValidInd()) {
            logger.error("用户：{}，被锁定！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，被锁定");
        }
        userRelationVo.setUserPo(userPo);

        // 查询角色信息
        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserIdAndValidIndIsTrue(userPo.getId());
        if (CollectionUtils.isEmpty(userRolePos)) {
            logger.error("用户：{}，未分配角色！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
        }
        userRelationVo.setUserRolePoList(userRolePos);

        // 查询角色信息
        List<RolePo> rolePoList = userRolePos.stream()
                .map(ur -> {
                    Long roleId = ur.getRoleId();
                    Optional<RolePo> rolePo = roleDao.findById(roleId);
                    if (!rolePo.isPresent()) {
                        logger.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
                        return null;
                    }
                    return rolePo.get();
                })
                .filter(Objects::nonNull)
                .toList();
        userRelationVo.setRolePoList(rolePoList);
        return userRelationVo;
    }
}
