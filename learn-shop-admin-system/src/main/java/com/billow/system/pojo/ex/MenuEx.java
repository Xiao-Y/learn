//package com.billow.system.pojo.ex;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * 菜单
// *
// * @author liuyongtao
// * @create 2018-05-26 9:30
// */
//@Data
//public class MenuEx implements Serializable {
//
//    @Schema(title = "菜单ID")
//    private String id;
//
//    @Schema(title = "菜单父ID")
//    private Long pid;
//
//    @Schema(title = "菜单标题")
//    private String title;
//
//    @Schema(title = "菜单标题CODE")
//    private String titleCode;
//
//    @Schema(title = "菜单图标")
//    private String icon;
//
//    @Schema(title = "有效标志")
//    private Boolean validInd;
//
//    @Schema(title = "是否显示")
//    private Boolean display;
//
//    @Schema(title = "子级菜单")
//    private List<MenuEx> children;
//
//    @Schema(title = "是否有子菜单显示")
//    private Boolean isChildrenDisplay;
//
//    @Schema(title = "显示的位置")
//    private Double sortField;
//
//}
