package com.billow.system.pojo.ex;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-26 9:30
 */
@Data
public class MenuEx {
    /**
     * 菜单ID
     */
    private String id;
    /**
     * 菜单父ID
     */
    private Long pid;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单标题CODE
     */
    private String titleCode;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 有效标志
     */
    private Boolean validInd;
    /**
     * 是否显示
     */
    private Boolean display;
    /**
     * 子级菜单
     */
    private List<MenuEx> children;
    /**
     * 是否有子菜单显示
     */
    private Boolean isChildrenDisplay;
    /**
     * 显示的位置
     */
    private Double sortField;

}
