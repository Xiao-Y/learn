package com.billow.system.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单权限 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-26
 */
@Data
@Accessors(chain = true)
public class MenuPermissionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long menuId;

    private Long permissionId;


}
