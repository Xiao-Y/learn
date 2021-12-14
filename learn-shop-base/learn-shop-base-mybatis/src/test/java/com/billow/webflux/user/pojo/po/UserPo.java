package com.billow.webflux.user.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("u_user")
@ApiModel(value="UserPo对象", description="")
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

    @TableField("descritpion")
    private String descritpion;

    @TableField("group_id")
    private String groupId;


}
