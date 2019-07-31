package com.billow.user.pojo.ex;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoleEx implements Serializable {
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
}
