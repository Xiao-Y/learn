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

    List<Long> roleIds = new ArrayList<>();
}
