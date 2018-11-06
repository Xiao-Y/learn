package com.billow.zuul.service;

import com.billow.tools.http.HttpUtils;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.zuul.pojo.UserInfo;
import com.billow.zuul.pojo.UserRole;
import com.billow.zuul.pojo.re.RoleRe;
import com.billow.zuul.remote.system.RoleRemote;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public class UserRoleService {

    @Autowired
    private RoleRemote roleRemote;

    public List<UserRole> getRoleByUser(UserInfo user) {
        BaseResponse<List<RoleRe>> baseResponse = roleRemote.findRolesInfoByUserId(user.getUserId());
        List<RoleRe> roleReList = HttpUtils.getResData(baseResponse);
        if (ToolsUtils.isNotEmpty(roleReList)) {
            List<UserRole> userRoles = Lists.newArrayList();
            roleReList.stream().forEach(f -> {
                UserRole userRole = new UserRole(f.getRoleCode());
                userRoles.add(userRole);
            });
            return userRoles;
        }
        return null;
    }
}
