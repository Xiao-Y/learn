package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.MenuPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-0:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends MenuPo implements Serializable {


    @ApiModelProperty("菜单ids，用于删除")
    Set<String> ids;

    @ApiModelProperty("角色集合")
    private List<RoleVo> roleVos;

    @ApiModelProperty("当前用户名")
    private String userCode;

}
