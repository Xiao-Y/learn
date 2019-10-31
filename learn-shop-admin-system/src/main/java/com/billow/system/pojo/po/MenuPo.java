package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_menu")
public class MenuPo extends BasePo implements Serializable {

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单CODE")
    private String menuCode;

    @ApiModelProperty("菜单描述")
    private String descritpion;

    @ApiModelProperty("父节点id")
    private Long pid;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("是否显示")
    private Boolean display;

    @ApiModelProperty("显示的位置")
    private Double sortField;

}
