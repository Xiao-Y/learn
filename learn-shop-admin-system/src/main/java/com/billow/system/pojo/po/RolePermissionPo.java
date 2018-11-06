package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * role与permission关联关系 多对多
 */
@Entity
@Table(name = "r_role_permission")
public class RolePermissionPo extends BasePo implements Serializable {

    // 角色id
    private Long roleId;
    // 权限id
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public RolePermissionPo setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public RolePermissionPo setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    @Override
    public String toString() {
        return "RolePermissionPo{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
