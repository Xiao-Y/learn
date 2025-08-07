package com.billow.user.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Entity
@Table(name = "sys_user_role")
public class UserRolePo extends BasePo implements Serializable {
    // 用户id
    private Long userId;
    // 角色id
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public UserRolePo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserRolePo setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public String toString() {
        return "UserRolePo{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
