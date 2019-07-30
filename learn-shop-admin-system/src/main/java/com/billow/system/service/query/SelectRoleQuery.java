package com.billow.system.service.query;

/**
 * 角色下拉列表
 *
 * @author liuyongtao
 * @create 2019-07-30 16:04
 */
public interface SelectRoleQuery {

    Long getId();

    /**
     * 角色名称
     */
    String getRoleName();

    /**
     * 角色CODE
     */
    String getRoleCode();
}
