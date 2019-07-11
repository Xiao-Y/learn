package com.billow.auth.pojo.po;



import com.billow.auth.pojo.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限(菜单)
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
@Entity
@Table(name = "sys_permission")
public class PermissionPo extends BasePo implements Serializable {
    //权限名称
    private String permissionName;
    // 权限CODE
    private String permissionCode;
    //权限描述
    private String descritpion;
    //授权链接
    private String url;
    // 系统模块
    private String systemModule;


    /**
     * 权限名称
     *
     * @return
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * 权限名称
     *
     * @param permissionName
     * @return
     */
    public PermissionPo setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    /**
     * 权限CODE
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/5/29 9:13
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 权限CODE
     *
     * @param permissionCode
     * @return com.billow.pojo.po.sys.PermissionPo
     * @author LiuYongTao
     * @date 2018/5/29 9:13
     */
    public PermissionPo setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
        return this;
    }

    /**
     * 权限描述
     *
     * @return
     */
    public String getDescritpion() {
        return descritpion;
    }

    /**
     * 权限描述
     *
     * @param descritpion
     * @return
     */
    public PermissionPo setDescritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    /**
     * 授权链接
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 授权链接
     *
     * @param url
     * @return
     */
    public PermissionPo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSystemModule() {
        return systemModule;
    }

    public PermissionPo setSystemModule(String systemModule) {
        this.systemModule = systemModule;
        return this;
    }
}
