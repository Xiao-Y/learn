package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.PermissionPo;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "权限ids，用于删除")
    Set<String> ids;

    @Schema(title = "角色集合")
    private List<RoleVo> roleVos;

    @Schema(title = "当前用户名")
    private String userCode;

//    @Schema(title = "拆分下拉多选")
//    private List<String> systemModules = new ArrayList<>();

    @Schema(title = "权限绑定的菜单id")
    private List<Long> menuIds;

//    // 合并下拉多选
//    public PermissionVo setSystemModules(List<String> systemModules) {
//        this.systemModules = systemModules;
//        // 转换数据结构,用于保存
//        if (ToolsUtils.isNotEmpty(systemModules)) {
//            this.setSystemModule(StringUtils.join(systemModules, ","));
//        }
//        return this;
//    }
}
