package com.billow.system.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限
 * </p>
 *
 * @author billow
 * @since 2021-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu_permission")
@ApiModel(value="MenuPermissionPo对象", description="菜单权限")
public class MenuPermissionPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("menu_id")
    private Long menuId;

    @TableField("permission_id")
    private Long permissionId;


}
