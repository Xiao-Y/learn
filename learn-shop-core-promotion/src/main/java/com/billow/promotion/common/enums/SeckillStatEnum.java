package com.billow.promotion.common.enums;

/**
 * 秒杀状态
 *
 * @author liuyongtao
 * @since 2021-1-22 10:01
 */
public enum SeckillStatEnum {
    FAIL(99, "失败"),
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "达到秒杀上限"),
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