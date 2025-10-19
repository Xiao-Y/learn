package com.billow.system.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

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
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sys_menu")
@Schema(title = "MenuPo对象", description = "")
public class MenuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("menu_code")
    private String menuCode;

    @Column("menu_name")
    private String menuName;

    @Column("pid")
    private Long pid;

    @Column("display")
    private Boolean display;

    @Column("icon")
    private String icon;

    @Column("description")
    private String description;

    @Column("sort_field")
    private Double sortField;


}
