package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
@Data
@Entity
@Table(name = "sys_menu")
public class MenuPo extends BasePo implements Serializable {
    //菜单名称
    private String menuName;
    // 菜单CODE
    private String menuCode;
    //菜单描述
    private String descritpion;
    //菜单链接
    private String url;
    //父节点id
    private Long pid;
    // 图标
    private String icon;
    // 是否显示
    private Boolean display;

}
