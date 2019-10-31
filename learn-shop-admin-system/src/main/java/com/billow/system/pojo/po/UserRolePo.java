package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "r_user_role")
public class UserRolePo extends BasePo implements Serializable {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("角色id")
    private Long roleId;

}
