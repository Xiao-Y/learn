package com.billow.system.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.mybatisflex.annotation.Table;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mybatisflex.annotation.Column;

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
@Table("sys_permission")
@Schema(title = "PermissionPo对象", description="")
public class PermissionPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("permission_name")
    private String permissionName;

    @Column("permission_code")
    private String permissionCode;

    @Column("url")
    private String url;

    @Column("system_module")
    private String systemModule;

    @Column("description")
    private String description;

    @Column("display")
    private Boolean display;

    @Column("icon")
    private String icon;

    @Column("pid")
    private Long pid;


}
