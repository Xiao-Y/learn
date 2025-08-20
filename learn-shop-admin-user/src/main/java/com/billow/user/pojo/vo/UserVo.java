package com.billow.user.pojo.vo;


import com.billow.user.pojo.po.UserPo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "角色id集合")
    List<Long> roleIds = new ArrayList<>();

    @Schema(title = "旧密码")
    private String oldPassWord;

    @Schema(title = "新密码")
    private String newPassWord;

    @Schema(title = "级联地址")
    private String[] casAddress = new String[0];

    @Schema(title = "显示用")
    private String showAddress;

    @Schema(title = "文件保存的完整路径（用户的头像）")
    private String filePath;

    @Schema(title = "文件名（不带后缀）")
    private String newFileName;
}
