package com.billow.system.pojo.po;


import com.billow.common.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "u_user")
public class UserPo extends BasePo implements Serializable {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户code")
    private String usercode;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("加密")
    private String salt;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("年龄")
    private Integer age;
}
