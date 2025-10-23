package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2025-10-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleSearchParam extends BasePage implements Serializable {

    @Column("role_code")
    private String roleCode;

    @Column("role_name")
    private String roleName;

    @Schema(title = "选种的权限")
    private List<Long> permissionChecked = new ArrayList<>();

    @Schema(title = "原始选种的权限")
    private List<Long> oldPermissionChecked = new ArrayList<>();

    @Schema(title = "选种的菜单")
    private List<String> menuChecked = new ArrayList<>();

    @Schema(title = "原始选种的菜单")
    private List<String> oldMenuChecked = new ArrayList<>();

    @Schema(title = "是否是新添加的角色")
    private Boolean isNewRole = true;
}
