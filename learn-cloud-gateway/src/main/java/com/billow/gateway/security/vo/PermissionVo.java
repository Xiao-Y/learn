package com.billow.gateway.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限对象
 *
 * @author liuyongtao
 * @create 2019-07-16 14:33
 */
@Data
public class PermissionVo  implements Serializable {

    private Long id;
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
}
