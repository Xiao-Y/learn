package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
@Schema(title = "MenuPo对象", description="")
public class MenuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "唯一")
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
