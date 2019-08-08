package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.PermissionPo;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-26 10:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionVo extends PermissionPo implements Serializable {

    @ApiModelProperty("权限ids，用于删除")
    Set<String> ids;

    @ApiModelProperty("角色集合")
    private List<RoleVo> roleVos;

    @ApiModelProperty("当前用户名")
    private String userCode;

    @ApiModelProperty("拆分下拉多选")
    private List<String> systemModules = new ArrayList<>();

    @ApiModelProperty("是否选种")
    private boolean checked;

    // 合并下拉多选
    public PermissionVo setSystemModules(List<String> systemModules) {
        this.systemModules = systemModules;
        // 转换数据结构,用于保存
        if (ToolsUtils.isNotEmpty(systemModules)) {
            this.setSystemModule(StringUtils.join(systemModules, ","));
        }
        return this;
    }
}
