package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("u_user")
@Schema(title = "UserPo对象", description="")
public class UserPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("usercode")
    private String usercode;

    @TableField("username")
    private String username;

    @TableField("sex")
    private String sex;

    @TableField("password")
    private String password;

    @TableField("address")
    private String address;

    @TableField("birth_date")
    private LocalDateTime birthDate;

    @TableField("phone")
    private String phone;

    @TableField("icon_url")
    private String iconUrl;

    @TableField("description")
    private String description;

    @TableField("group_id")
    private String groupId;


}
