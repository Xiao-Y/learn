package com.billow.system.pojo.cache;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author liuyongtao
 * @since 2021-1-29 14:47
 */
@Data
public class PermissionCache {

    @Schema(title = "主键Id")
    private Long id;

    @Schema(title = "权限名称")
    private String permissionName;

    @Schema(title = "权限CODE")
    private String permissionCode;

    @Schema(title = "权限描述")
    private String description;

    @Schema(title = "授权链接")
    private String url;

    @Schema(title = "系统模块")
    private String systemModule;
}
