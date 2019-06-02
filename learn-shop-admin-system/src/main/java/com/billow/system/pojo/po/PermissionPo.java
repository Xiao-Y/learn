package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
@Entity
@Table(name = "sys_permission")
@Data
public class PermissionPo extends BasePo implements Serializable {
    //权限描述
    private String descritpion;
    //授权链接
    private String url;
}
