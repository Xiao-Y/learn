package com.billow.system.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("sys_role_permission")
@Schema(title = "RolePermissionPo对象", description="")
public class RolePermissionPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("role_id")
    private Long roleId;

    @Column("permission_id")
    private Long permissionId;


}
