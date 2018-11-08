package com.billow.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangyunfei on 2017/6/9.
 */
//@Service("userDetailsService")
@Service
public class DomainUserDetailsService implements UserDetailsService {

//    @Autowired
//    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String lowcaseUsername = username.toLowerCase();
//        Optional<SysUser> realUser = sysUserRepository.findOneWithRolesByUsername(lowcaseUsername);

//        return realUser.map(user -> {
//            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities();
//            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
//        }).orElseThrow(() -> new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!"));
        // 查询用户的权限
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        userAuthotities.add(new SimpleGrantedAuthority("query-demo"));
        return new User("admin", "admin", userAuthotities);
    }
}
