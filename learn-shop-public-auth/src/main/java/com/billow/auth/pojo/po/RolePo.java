package com.billow.auth.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_role")
public class RolePo extends BasePo implements Serializable {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色CODE
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String descritpion;

    /**
     * 角色名称
     *
     * @return
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 角色名称
     *
     * @param roleName
     */
    public RolePo setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    /**
     * 角色CODE
     *
     * @return
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 角色CODE
     *
     * @param roleCode
     */
    public RolePo setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    /**
     * 角色描述
     *
     * @return
     */
    public String getDescritpion() {
        return descritpion;
    }

    /**
     * 角色描述
     *
     * @param descritpion
     */
    public RolePo setDescritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    @Override
    public String toString() {
        return "RolePo{" +
                "roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", descritpion='" + descritpion + '\'' +
                '}';
    }
}
