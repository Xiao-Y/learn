package com.billow.gateway.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RolePo implements Serializable {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色CODE
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String description;

    // 主键id
    private Long id;
    // 创建人
    private String creatorCode;
    // 创建人
    private String updaterCode;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
    // 有效标志
    private Boolean validInd;
}
