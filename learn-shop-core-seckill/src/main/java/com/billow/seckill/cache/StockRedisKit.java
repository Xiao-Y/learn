package com.billow.seckill.cache;

import com.billow.seckill.enums.LuaScriptEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

/**
 * 库存相关操作的工具类
 *
 * @author liuyongtao
 * @since 2021-8-9 8:43
 */
@Slf4j
public class StockRedisKit {

    /**
     * 扣减redis库存操作（库存不足时返回0）
     *
     * @param key     key
     * @param hashKey hashKey
     * @param num     扣减数量
     * @param mode    扣减策略
     * @return 实际扣减数量，如果 num <= 0 或者库存为 0 时，返回 0。
     */
    public static Long decrStock(String key, String hashKey, long num, RStore.DecrMode mode) {
        return LuaUtil.execute(LuaScriptEnum.DECR_STOCK,
                Collections.singletonList(key), hashKey,
                num,
                mode.getFlag());
    }

    /**
     * 扣减redis库存操作（扣除剩余库存）
     *
     * @param key     key
     * @param hashKey hashKey
     * @param num     扣减数量
     * @return 实际扣减数量，如果 num <= 0 或者库存为 0 时，返回 0。
     */
    public static Long decrStock(String key, String hashKey, long num) {
        return decrStock(key, hashKey, num, RStore.DecrMode.DECR);
    }

    /**
     * 增加redis库存操作
     *
     * @param key     key
     * @param hashKey hashKey
     * @param num     增加数量
     * @return 增加库存后数量，如果 num < 0,返回当前库存
     */
    public static Long incrStock(String key, String hashKey, long num) {
        return LuaUtil.execute(LuaScriptEnum.INCR_STOCK,
                Collections.singletonList(key), hashKey,
                num);
    }
}
