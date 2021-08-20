package com.billow.tools.constant;

/**
 * redis 的 key
 *
 * @author liuyongtao
 * @create 2019-07-30 11:19
 */
public class RedisCst {

    public final static String delimiter = ":";

    /**
     * 角色的权限信息：ROLE:PERMISSION:ADMIN
     */
    public final static String ROLE_PERMISSION_KEY = "ROLE:PERMISSION";

    /**
     * 角色的菜单信息：ROLE:MENU:ADMIN
     */
    public final static String ROLE_MENU_KEY = "ROLE:MENU";

    //==============================================================================================

    /**
     * 数据字典信息：COMM:DICTIONARY_FIELD_TYPE->dataSourcesType,mailType...
     */
    public final static String COMM_DICTIONARY_FIELD_TYPE = "COMM:DIC_FIELD_TYPE";

    /**
     * 数据字典信息：COMM:CITY:TREE
     */
    public final static String COMM_CITY_TREE = "COMM:CITY:TREE";
    /**
     * 数据字典信息：COMM:CITY:ONE
     */
    public final static String COMM_CITY_ONE = "COMM:CITY:ONE";

    /**
     * 路由信息 COMM:ROUTE_INFO
     */
    public final static String COMM_ROUTE_INFO = "COMM:ROUTE_INFO";
    /**
     * 数据恢复
     *
     * @author billow
     * @date 2019/8/11 13:53
     */
    public final static String COMM_DATA_RECOVERY = "COMM:DATA_RECOVERY";

    //==============================================================================================

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
    //==============================================================================================
    /**
     * 秒杀用户锁定前缀。SECKILL:LOCK:{seckillId}:{userCode}
     */
    public final static String SECKILL_LOCK = "SECKILL:LOCK";
    /**
     * 秒杀库存前缀，SECKILL:STOCK:{seckillId}:{userCode}
     */
    public final static String SECKILL_STOCK = "SECKILL:STOCK";
    /**
     * 秒杀商品信息前缀，SECKILL:PRODUCT:{seckillId}
     */
    public final static String SECKILL_PRODUCT = "SECKILL:PRODUCT";
    //==============================================================================================

    /**
     * 菜单管理里面的菜单树，MENU:MENU_TREE
     */
    public final static String MENU_MENU_TREE = "MENU:MENU_TREE";

    /**
     * 根据id查询菜单信息，MENU:MENU_ID
     */
    public final static String MENU_MENU_ID = "MENU:MENU_ID";


    /**
     * 生成key
     *
     * @param obj
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-6-11 10:00
     */
    public static String genKey(String... obj) {
        return String.join(delimiter, obj);
    }

}
