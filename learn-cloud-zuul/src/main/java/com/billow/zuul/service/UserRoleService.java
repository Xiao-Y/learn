package com.billow.zuul.service;

import com.billow.zuul.pojo.UserInfo;
import com.billow.zuul.pojo.UserRole;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public class UserRoleService {

    public List<UserRole> getRoleByUser(UserInfo user) {
        if ("admin".equals(user.getUserName())) {
            //@see ExpressionUrlAuthorizationConfigurer  private static String hasAnyRole(String... authorities) 带 ROLE_
            return Lists.newArrayList(new UserRole("ROLE_ADMIN"));
        } else if ("test".equals(user.getUserName())) {
            return Lists.newArrayList(new UserRole("ROLE_TEST"));
        }
        return null;
    }
}
