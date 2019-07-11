package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */

@Data
@Entity
@Table(name = "sys_permission")
public class PermissionPo extends BasePo implements Serializable {
    //权限名称
    private String permissionName;
    // 权限CODE
    private String permissionCode;
    //权限描述
    private String descritpion;
    //授权链接
    private String url;
    // 系统模块
    private String systemModule;

}
