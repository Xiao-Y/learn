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
 * 
 * </p>
 *
 * @author billow
 * @since 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="MenuPo对象", description="")
public class MenuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一")
    @TableField("menu_code")
    private String menuCode;

    @TableField("menu_name")
    private String menuName;

    @TableField("pid")
    private Long pid;

    @TableField("display")
    private Boolean display;

    @TableField("icon")
    private String icon;

    @TableField("description")
    private String description;

    @TableField("sort_field")
    private Double sortField;


}
