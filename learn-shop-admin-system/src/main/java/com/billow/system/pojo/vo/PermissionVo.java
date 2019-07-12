package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.PermissionPo;
import com.billow.tools.utlis.ToolsUtils;
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
public class PermissionVo extends PermissionPo implements Serializable {

    /**
     * 权限ids，用于删除
     */
    Set<String> ids;
    /**
     * 角色集合
     */
    private List<RoleVo> roleVos;
    /**
     * 当前用户名
     */
    private String userCode;
    /**
     * 拆分下拉多选
     */
    private List<String> systemModules = new ArrayList<>();

    /**
     * 是否选种
     */
    private boolean checked;

    public List<RoleVo> getRoleVos() {
        return roleVos;
    }

    public PermissionVo setRoleVos(List<RoleVo> roleVos) {
        this.roleVos = roleVos;
        return this;
    }

    public String getUserCode() {
        return userCode;
    }

    public PermissionVo setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public Set<String> getIds() {
        return ids;
    }

    public PermissionVo setIds(Set<String> ids) {
        this.ids = ids;
        return this;
    }

    public List<String> getSystemModules() {
        return systemModules;
    }

    public PermissionVo setSystemModules(List<String> systemModules) {
        this.systemModules = systemModules;
        // 转换数据结构,用于保存
        if (ToolsUtils.isNotEmpty(systemModules)) {
            this.setSystemModule(StringUtils.join(systemModules, ","));
        }
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public PermissionVo setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }
}
