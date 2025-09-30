package com.billow.gateway.security.service;

import com.billow.gateway.pojo.po.RolePo;
import com.billow.gateway.pojo.po.UserPo;
import com.billow.gateway.pojo.po.UserRolePo;
import com.billow.gateway.pojo.vo.UserRelationVo;
import com.billow.gateway.security.service.redis.RoleRedisKit;
import com.billow.gateway.security.service.redis.UserRedisKit;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRedisKit userRedisKit;
    @Autowired
    private RoleRedisKit roleRedisKit;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        log.info("查询用户：{} 的信息...", usercode);
        UserRelationVo userRelationVo = this.queryUserRelationByUsercode(usercode);

        // 查询角色信息
        List<RolePo> rolePoList = userRelationVo.getRolePoList();
        if (CollectionUtils.isEmpty(rolePoList)) {
            log.error("用户：{}，未分配权限！", usercode);
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
            log.error("用户名不能为空！");
            throw new UsernameNotFoundException("用户名不能为空！");
        }
        UserRelationVo userRelationVo = new UserRelationVo();

        // 查询用户信息
        UserPo userPo = userRedisKit.getUserInfoCache(usercode);
        if (userPo == null) {
            log.error("用户：{}，不存在！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
        }
        if (!userPo.getValidInd()) {
            log.error("用户：{}，被锁定！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，被锁定");
        }
        userRelationVo.setUserPo(userPo);

        // 查询角色信息
        List<UserRolePo> userRolePos = userRedisKit.getUserRoleCache(usercode);
        if (CollectionUtils.isEmpty(userRolePos)) {
            log.error("用户：{}，未分配角色！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
        }
        userRelationVo.setUserRolePoList(userRolePos);

        // 查询角色信息
        List<RolePo> rolePoList = userRolePos.stream()
                .map(ur -> {
                    Long roleId = ur.getRoleId();

                    RolePo rolePo = roleRedisKit.getRoleInfoCache(roleId);
                    if (rolePo == null || !rolePo.getValidInd()) {
                        log.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
                        return null;
                    }
                    return rolePo;
                })
                .filter(Objects::nonNull)
                .toList();
        userRelationVo.setRolePoList(rolePoList);
        return userRelationVo;
    }
}
