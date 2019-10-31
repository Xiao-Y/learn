package com.billow.system.pojo.ex;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEx extends BasePo implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户code")
    private String usercode;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("出生日期")
    private Date birthDate;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("用户描述")
    private String descritpion;

    @ApiModelProperty("用户头像")
    private String iconUrl;

    @ApiModelProperty("用户分组")
    private String groupId;
}
