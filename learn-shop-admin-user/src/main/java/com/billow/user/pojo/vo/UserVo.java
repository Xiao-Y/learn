package com.billow.user.pojo.vo;


import com.billow.user.pojo.po.UserPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 *
 * @author liuyongtao
 * @create 2018-05-16 10:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends UserPo {

    @ApiModelProperty("角色id集合")
    List<Long> roleIds = new ArrayList<>();

    @ApiModelProperty("旧密码")
    private String oldPassWord;

    @ApiModelProperty("新密码")
    private String newPassWord;

    @ApiModelProperty("级联地址")
    private String[] casAddress = new String[0];

    @ApiModelProperty("显示用")
    private String showAddress;
}
