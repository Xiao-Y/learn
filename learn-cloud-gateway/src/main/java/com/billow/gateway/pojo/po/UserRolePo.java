package com.billow.gateway.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class UserRolePo extends BasePo implements Serializable {
    // 用户id
    private Long userId;
    // 角色id
    private Long roleId;
}
