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
     * 角色的权限信息：role:permission:admin
     */
    public final static String ROLE_PERMISSION_KEY = "role:permission";

    /**
     * 角色的菜单信息：role:menu:admin
     */
    public final static String ROLE_MENU_KEY = "role:menu";

    //==============================================================================================

    /**
     * 数据字典信息：comm:dictionary_field_type->dataSourcesType,mailType...
     */
    public final static String COMM_DICTIONARY_FIELD_TYPE = "comm:dic_field_type";

    /**
     * 数据字典信息：comm:city:tree
     */
    public final static String COMM_CITY_TREE = "comm:city:tree";
    /**
     * 数据字典信息：comm:city:one
     */
    public final static String COMM_CITY_ONE = "comm:city:one";

    /**
     * 路由信息 comm:route_info
     */
    public final static String COMM_ROUTE_INFO = "comm:route_info";
    /**
     * 数据恢复
     *
     * @author billow
     * @date 2019/8/11 13:53
     */
    public final static String COMM_DATA_RECOVERY = "comm:data_recovery";

    //==============================================================================================

    /**
     * 黑名单-修改过用户信息：blacklist:edituser:xxx
     */
    public final static String BLACKLIST_EDITUSER = "blacklist:edituser:";
    /**
     * 黑名单-修改过用户信息：value 中的 key
     */
    public final static String BLACKLIST_EDITUSER_OLDUSER = "old_user";
    /**
     * 黑名单-修改过用户信息：value 中的 key
     */
    public final static String BLACKLIST_EDITUSER_ROLECODES = "role_codes";
    //==============================================================================================
    /**
     * 秒杀用户锁定前缀。seckill:lock:{seckillProductId}:{userCode}
     */
    public final static String SECKILL_LOCK = "seckill:lock";
    /**
     * 秒杀库存前缀，seckill:product:stock:{seckillProductId}
     */
    public final static String SECKILL_PRODUCT_STOCK = "seckill:product:stock";
    /**
     * 秒杀商品信息前缀，seckill:product:info:{seckillProductId}
     */
    public final static String SECKILL_PRODUCT_INFO = "seckill:product:info";

    /**
     * 秒杀信息前缀，seckill:info:{seckillId}
     */
    public final static String SECKILL_INFO = "seckill:info";

    /**
     * 秒杀场次信息前缀，seckill:session:{seckillSessionId}
     */
    public final static String SECKILL_SESSION = "seckill:session";
    //==============================================================================================
    /**
     * 订单信息前缀。order:info:{orderSn}
     */
    public final static String ORDER_INFO = "order:info";

    //==============================================================================================

    /**
     * 根据id查询菜单信息，menu:menu_id
     */
    public final static String MENU_MENU_ID = "menu:menu_id";


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
