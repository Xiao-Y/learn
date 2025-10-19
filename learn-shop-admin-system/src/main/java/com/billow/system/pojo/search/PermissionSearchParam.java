package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.billow.system.pojo.vo.RoleVo;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionSearchParam extends BasePage implements Serializable {

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

    // 有效标志
    @Column(value = "valid_ind")
    private Boolean validInd;

    @Schema(title = "权限ids，用于删除")
    Set<String> ids;

    @Schema(title = "角色集合")
    private List<RoleVo> roleVos;

    @Schema(title = "当前用户名")
    private String userCode;

    @Schema(title = "权限绑定的菜单id")
    private List<Long> menuIds;
}
