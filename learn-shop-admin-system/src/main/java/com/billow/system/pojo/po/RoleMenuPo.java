package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * role与menu关联关系 多对多
 */
@Data
@Entity
@Table(name = "r_role_menu")
public class RoleMenuPo extends BasePo implements Serializable {

    // 角色id
    private Long roleId;
    // 权限id
    private Long menuId;


}
