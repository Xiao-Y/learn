package com.billow.gateway.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserPo implements Serializable {

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "用户code")
    private String usercode;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "性别")
    private String sex;

    @Schema(title = "出生日期")
    private Date birthDate;

    @Schema(title = "手机号")
    private String phone;

    @Schema(title = "地址")
    private String address;

    @Schema(title = "用户描述")
    private String description;

    @Schema(title = "用户头像")
    private String iconUrl;

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
