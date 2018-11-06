package com.billow.zuul.pojo.re;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-11-06 15:03
 */
public class RoleRe {
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
    private Long id;
    private String creatorCode;
    private String updaterCode;
    private Boolean validInd;
    private Date createTime;
    private Date updateTime;

    public String getRoleName() {
        return roleName;
    }

    public RoleRe setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public RoleRe setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public RoleRe setDescritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RoleRe setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public RoleRe setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
        return this;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public RoleRe setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
        return this;
    }

    public Boolean getValidInd() {
        return validInd;
    }

    public RoleRe setValidInd(Boolean validInd) {
        this.validInd = validInd;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public RoleRe setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public RoleRe setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
