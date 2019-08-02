package com.billow.user.pojo.po;




import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "u_user")
public class UserPo extends BasePo implements Serializable {
    // 用户名
    private String username;
    // 用户code
    private String usercode;
    // 密码
    private String password;
    // 性别
    private String sex;
    // 出生日期
    private Date birthDate;
    // 手机号
    private String phone;
    // 地址
    private String address;
    // 用户描述
    private String descritpion;
}
