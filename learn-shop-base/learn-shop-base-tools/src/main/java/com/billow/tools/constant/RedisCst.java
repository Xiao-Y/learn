package com.billow.tools.constant;

/**
 * redis 的 key
 *
 * @author liuyongtao
 * @create 2019-07-30 11:19
 */
public class RedisCst {

    /**
     * 角色的权限信息：ROLE:PERMISSION:ADMIN
     */
    public final static String ROLE_PERMISSION_KEY = "ROLE:PERMISSION:";

    /**
     * 角色的菜单信息：ROLE:MENU:ADMIN
     */
    public final static String ROLE_MENU_KEY = "ROLE:MENU:";

    /**
     * 数据字典信息：COMM:DIC_SYS_MODULE:adminSystem
     */
    public final static String COMM_DICTIONARY_SYS_MODULE = "COMM:DIC_SYS_MODULE:";

    /**
     * 数据字典信息：COMM:DIC_SYS_MODULE:adminSystem,adminUser,publicJob
     */
    public final static String COMM_DICTIONARY_SYS_MODULE_LIST = "COMM:DIC_SYS_MODULE";
    /**
     * 数据字典信息：COMM:DICTIONARY_FIELD_TYPE->dataSourcesType,mailType...
     */
    public final static String COMM_DICTIONARY_FIELD_TYPE = "COMM:DIC_FIELD_TYPE";

    /**
     * 数据字典信息：COMM:CITY
     */
    public final static String COMM_CITY = "COMM:CITY";

    /**
     * 路由信息 COMM:ROUTE_INFO
     */
    public final static String GATEWAY_ROUTE_INFO = "COMM:ROUTE_INFO";
    /**
     * 数据恢复
     *
     * @author billow
     * @date 2019/8/11 13:53
     */
    public final static String COMM_DATA_RECOVERY = "COMM:DATA_RECOVERY";

    /**
     * 黑名单-修改过用户信息：BLACKLIST:EDITUSER:xxx
     */
    public final static String BLACKLIST_EDITUSER = "BLACKLIST:EDITUSER:";
    /**
     * 黑名单-修改过用户信息：value 中的 key
     */
    public final static String BLACKLIST_EDITUSER_OLDUSER = "OLD_USER";
    /**
     * 黑名单-修改过用户信息：value 中的 key
     */
    public final static String BLACKLIST_EDITUSER_ROLECODES = "ROLE_CODES";
    /**
     * 秒杀用户锁定前缀。SECKILL:LOCK:seckillId
     */
    public final static String SECKILL_LOCK = "SECKILL:LOCK:";
    /**
     * 秒杀库存前缀，SECKILL:STOCK:seckillId:userCode
     */
    public final static String SECKILL_STOCK = "SECKILL:STOCK:";
}
