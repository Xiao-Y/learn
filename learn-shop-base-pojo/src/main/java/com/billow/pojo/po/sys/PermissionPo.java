package com.billow.pojo.po.sys;

import com.billow.pojo.po.base.BasePo;

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
    //父节点id
    private Long pid;
    // 图标
    private String icon;
    // 是否显示
    private Boolean display;

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

    /**
     * 父节点id
     *
     * @return
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 父节点id
     *
     * @param pid
     * @return
     */
    public PermissionPo setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public PermissionPo setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Boolean getDisplay() {
        return display;
    }

    public PermissionPo setDisplay(Boolean display) {
        this.display = display;
        return this;
    }

    @Override
    public String toString() {
        return "PermissionPo{" +
                "permissionName='" + permissionName + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                ", descritpion='" + descritpion + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                ", icon='" + icon + '\'' +
                ", display=" + display +
                "} " + super.toString();
    }
}
