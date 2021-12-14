package com.billow.webflux.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-14
 */
@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String usercode;

    private String username;

    private String sex;

    private String password;

    private String address;

    private LocalDateTime birthDate;

    private String phone;

    private String iconUrl;

    private String descritpion;

    private String groupId;


}
