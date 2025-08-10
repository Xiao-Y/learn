package com.billow.gateway.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
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
    private String description;
}
