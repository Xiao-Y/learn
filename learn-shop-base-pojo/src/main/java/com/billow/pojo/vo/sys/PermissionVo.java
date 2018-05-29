package com.billow.pojo.vo.sys;

import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.po.sys.PermissionPo;

import java.io.Serializable;
import java.util.List;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-26 10:15
 */
public class PermissionVo extends PermissionPo implements Serializable {

    /**
     * 角色集合
     */
    private List<RoleVo> roleVos;
    /**
     * 当前用户名
     */
    private String userCode;

    public List<RoleVo> getRoleVos() {
        return roleVos;
    }

    public PermissionVo setRoleVos(List<RoleVo> roleVos) {
        this.roleVos = roleVos;
        return this;
    }

    public String getUserCode() {
        return userCode;
    }

    public PermissionVo setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
}
