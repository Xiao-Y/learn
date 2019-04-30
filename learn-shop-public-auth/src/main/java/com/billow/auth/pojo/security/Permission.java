package com.billow.auth.pojo.security;

/**
 * 权限信息
 *
 * @author liuyongtao
 * @create 2019-04-30 11:24
 */
public class Permission {
    //权限名称
    private String permissionName;
    // 权限CODE
    private String permissionCode;
    //授权链接
    private String url;

    public String getPermissionName() {
        return permissionName;
    }

    public Permission setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public Permission setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }
}
