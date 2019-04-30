package com.billow.auth.pojo.security;

import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePo;
import com.billow.auth.pojo.po.UserPo;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 自定义用户详细信息
 *
 * @author liuyongtao
 * @create 2019-04-30 9:16
 */
public class CustomUserDetails implements UserDetails {

    private User user;
    private List<Role> roles;
    private List<Permission> permissions;

    public CustomUserDetails(UserPo userPo, List<RolePo> rolePos, List<PermissionPo> permissionPos) {
        this.user = ConvertUtils.convert(userPo, User.class);
        this.roles = ConvertUtils.convert(rolePos, Role.class);
        this.permissions = ConvertUtils.convert(permissionPos, Permission.class);
    }

    /**
     * 获取用户信息
     *
     * @return com.billow.auth.pojo.security.User
     * @author LiuYongTao
     * @date 2019/4/30 9:24
     */
    public User getUser() {
        return user;
    }

    /**
     * 获取用户角色信息
     *
     * @return com.billow.auth.pojo.security.User
     * @author LiuYongTao
     * @date 2019/4/30 9:24
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * 获取用户权限信息
     *
     * @return com.billow.auth.pojo.security.User
     * @author LiuYongTao
     * @date 2019/4/30 9:24
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsercode();
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 凭据是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否未可以
    @Override
    public boolean isEnabled() {
        return user.getValidInd();
    }

}
