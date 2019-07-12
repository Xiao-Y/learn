package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.RolePo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 *
 * @author liuyongtao
 * @create 2018-05-29 11:34
 */
@Data
public class RoleVo extends RolePo implements Serializable {

    private List<Long> permissionChecked = new ArrayList<>();
    private List<String> menuChecked = new ArrayList<>();
}
