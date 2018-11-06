package com.billow.tools.enums;

/**
 * redis 的key
 *
 * @author LiuYongTao
 * @date 2018/5/24 9:52
 */
public enum RdsKeyEnum {

    /**
     * "whiteList:", "白名单前缀"，如 whiteList:springApplicationName:clientIP
     */
    WHITE_LIST("whiteList:", "白名单前缀"),
    /**
     * "findMenus", "菜单管理里面的菜单树"，如 menusMag
     */
    FIND_MENUS("findMenus", "菜单管理里面的菜单树"),
    /**
     * "findMenuById", "菜单管理里面的菜单树"，如 findMenuById:id
     */
    FIND_MENU_BY_ID("findMenuById", "根据id查询菜单信息");

    private String key;
    private String value;

    RdsKeyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public RdsKeyEnum setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RdsKeyEnum setValue(String value) {
        this.value = value;
        return this;
    }
}
