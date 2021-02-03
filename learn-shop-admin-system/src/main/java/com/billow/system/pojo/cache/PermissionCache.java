package com.billow.system.pojo.cache;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyongtao
 * @since 2021-1-29 14:47
 */
@Data
public class PermissionCache {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限CODE")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    private String descritpion;

    @ApiModelProperty("授权链接")
    private String url;

    @ApiModelProperty("系统模块")
    private String systemModule;
}
