package com.billow.system.pojo.ex;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登陆用户的信息
 *
 * @author liuyongtao
 * @create 2018-05-29 17:47
 */
@Data
public class HomeEx implements Serializable {

    @ApiModelProperty("角色CODE集合")
    List<String> roleCodes = new ArrayList<>();

    @ApiModelProperty("菜单集合")
    List<MenuEx> menus = new ArrayList<>();
}
