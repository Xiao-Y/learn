package com.billow.system.pojo.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-26 9:30
 */
@Data
public class MenuEx implements Serializable {

    @ApiModelProperty("菜单ID")
    private String id;

    @ApiModelProperty("菜单父ID")
    private Long pid;

    @ApiModelProperty("菜单标题")
    private String title;

    @ApiModelProperty("菜单标题CODE")
    private String titleCode;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("有效标志")
    private Boolean validInd;

    @ApiModelProperty("是否显示")
    private Boolean display;

    @ApiModelProperty("子级菜单")
    private List<MenuEx> children;

    @ApiModelProperty("是否有子菜单显示")
    private Boolean isChildrenDisplay;

    @ApiModelProperty("显示的位置")
    private Double sortField;

}
