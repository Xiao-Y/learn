package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RolePermissionSearchParam extends BasePage implements Serializable {


    @Column("role_id")
    private Long roleId;

    @Column("permission_id")
    private Long permissionId;

    // 有效标志
    @Column(value = "valid_ind")
    private Boolean validInd;
}
