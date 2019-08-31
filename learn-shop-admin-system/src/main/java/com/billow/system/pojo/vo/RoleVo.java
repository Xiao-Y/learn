package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.RolePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 *
 * @author liuyongtao
 * @create 2018-05-29 11:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends RolePo implements Serializable {

    @ApiModelProperty("选种的权限")
    private List<Long> permissionChecked = new ArrayList<>();

    @ApiModelProperty("原始选种的权限")
    private List<Long> oldPermissionChecked = new ArrayList<>();

    @ApiModelProperty("选种的菜单")
    private List<String> menuChecked = new ArrayList<>();

    @ApiModelProperty("原始选种的菜单")
    private List<String> oldMenuChecked = new ArrayList<>();

    @ApiModelProperty("是否是新添加的角色")
    private Boolean isNewRole = true;
}
