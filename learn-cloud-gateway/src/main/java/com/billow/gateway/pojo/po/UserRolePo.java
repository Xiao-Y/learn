package com.billow.gateway.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user与role关联关系，多对多
 */
@Data
public class UserRolePo implements Serializable {
    // 用户id
    private Long userId;
    // 角色id
    private Long roleId;

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
