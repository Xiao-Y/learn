package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * role与permission关联关系 多对多
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "r_role_permission")
public class RolePermissionPo extends BasePo implements Serializable {


    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("权限id")
    private Long permissionId;
}
