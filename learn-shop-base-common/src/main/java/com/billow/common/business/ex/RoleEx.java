//package com.billow.common.business.ex;
//
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * 角色信息
// *
// * @author liuyongtao
// * @create 2018-05-29 11:34
// */
//public class RoleEx implements Serializable {
//
//    private Long id;
//    private String creatorCode;
//    private String updaterCode;
//    private Boolean validInd;
//    private Date createTime;
//    private Date updateTime;
//
//    /**
//     * 角色名称
//     */
//    private String roleName;
//    /**
//     * 角色CODE
//     */
//    private String roleCode;
//    /**
//     * 角色描述
//     */
//    private String descritpion;
//
//    /**
//     * 角色名称
//     *
//     * @return
//     */
//    public String getRoleName() {
//        return roleName;
//    }
//
//    /**
//     * 角色名称
//     *
//     * @param roleName
//     */
//    public RoleEx setRoleName(String roleName) {
//        this.roleName = roleName;
//        return this;
//    }
//
//    /**
//     * 角色CODE
//     *
//     * @return
//     */
//    public String getRoleCode() {
//        return roleCode;
//    }
//
//    /**
//     * 角色CODE
//     *
//     * @param roleCode
//     */
//    public RoleEx setRoleCode(String roleCode) {
//        this.roleCode = roleCode;
//        return this;
//    }
//
//    /**
//     * 角色描述
//     *
//     * @return
//     */
//    public String getDescritpion() {
//        return descritpion;
//    }
//
//    /**
//     * 角色描述
//     *
//     * @param descritpion
//     */
//    public RoleEx setDescritpion(String descritpion) {
//        this.descritpion = descritpion;
//        return this;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public RoleEx setId(Long id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getCreatorCode() {
//        return creatorCode;
//    }
//
//    public RoleEx setCreatorCode(String creatorCode) {
//        this.creatorCode = creatorCode;
//        return this;
//    }
//
//    public String getUpdaterCode() {
//        return updaterCode;
//    }
//
//    public RoleEx setUpdaterCode(String updaterCode) {
//        this.updaterCode = updaterCode;
//        return this;
//    }
//
//    public Boolean getValidInd() {
//        return validInd;
//    }
//
//    public RoleEx setValidInd(Boolean validInd) {
//        this.validInd = validInd;
//        return this;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public RoleEx setCreateTime(Date createTime) {
//        this.createTime = createTime;
//        return this;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public RoleEx setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return "RoleEx{" +
//                "id=" + id +
//                ", creatorCode='" + creatorCode + '\'' +
//                ", updaterCode='" + updaterCode + '\'' +
//                ", validInd=" + validInd +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                ", roleName='" + roleName + '\'' +
//                ", roleCode='" + roleCode + '\'' +
//                ", descritpion='" + descritpion + '\'' +
//                '}';
//    }
//}
