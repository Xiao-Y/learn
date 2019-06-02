package com.billow.system.pojo.vo;


import com.billow.system.pojo.po.MenuPo;
import com.billow.system.pojo.po.PermissionPo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 *
 * @author liuyongtao
 * @create 2018-05-0:15
 */
public class MenuVo extends MenuPo implements Serializable {

    /**
     * 菜单ids，用于删除
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

    public List<RoleVo> getRoleVos() {
        return roleVos;
    }

    public MenuVo setRoleVos(List<RoleVo> roleVos) {
        this.roleVos = roleVos;
        return this;
    }

    public String getUserCode() {
        return userCode;
    }

    public MenuVo setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public Set<String> getIds() {
        return ids;
    }

    public MenuVo setIds(Set<String> ids) {
        this.ids = ids;
        return this;
    }
}
