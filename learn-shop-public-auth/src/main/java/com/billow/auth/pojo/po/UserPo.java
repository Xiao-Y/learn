package com.billow.auth.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "u_user")
public class UserPo extends BasePo implements Serializable {

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
}
