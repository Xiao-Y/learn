package com.billow.user.pojo.vo;


import com.billow.user.pojo.po.UserPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 *
 * @author liuyongtao
 * @create 2018-05-16 10:29
 */
@Data
public class UserVo extends UserPo {

    // 角色id集合
    List<Long> roleIds = new ArrayList<>();
    // 旧密码
    private String oldPassWord;
    // 新密码
    private String newPassWord;
}
