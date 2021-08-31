package com.billow.promotion.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lua 脚本文件枚举
 *
 * @author liuyongtao
 * @since 2021-8-12 8:35
 */
@Getter
@AllArgsConstructor
public enum LuaScriptEnum {

    SEC_KILL("seckill.lua", "秒杀：校验是否秒杀过，减库存，插入订单信息"),
    DECR_STOCK("decrStock.lua", "增加库存"),
    INCR_STOCK("incrStock.lua", "减库存");

    private String fileName;
    private String desc;

}
