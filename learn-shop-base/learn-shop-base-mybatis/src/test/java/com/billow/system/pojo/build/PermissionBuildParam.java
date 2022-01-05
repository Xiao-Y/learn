package com.billow.system.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2022-01-04
 */
@Data
@Accessors(chain = true)
public class PermissionBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String permissionName;

    private String permissionCode;

    private String url;

    private String systemModule;

    private String description;

    private Boolean display;

    private String icon;

    private Long pid;


}
