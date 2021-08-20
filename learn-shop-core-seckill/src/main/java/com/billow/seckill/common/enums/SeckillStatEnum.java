package com.billow.seckill.common.enums;

/**
 * 秒杀状态
 *
 * @author liuyongtao
 * @since 2021-1-22 10:01
 */
public enum SeckillStatEnum {
    UNDEFIND(99, "未知"),
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据串改"),
    STOCK_OUT(-4, "库存不足"),
    PRODUCT_NOT_EXIST(-6, "商品不存在");

    private int state;
    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static String stateOf(int index) {
        for (SeckillStatEnum state : values()) {
            if (state.getState() == index) {
                return state.stateInfo;
            }
        }
        return null;
    }

    public static SeckillStatEnum of(int index) {
        for (SeckillStatEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}