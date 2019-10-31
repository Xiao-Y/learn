package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_permission")
public class PermissionPo extends BasePo implements Serializable {

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限CODE")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    private String descritpion;

    @ApiModelProperty("授权链接")
    private String url;

    @ApiModelProperty("系统模块")
    private String systemModule;

}
