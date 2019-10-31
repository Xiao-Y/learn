package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
public class RolePo extends BasePo implements Serializable {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色CODE")
    private String roleCode;

    @ApiModelProperty("角色描述")
    private String descritpion;
}
