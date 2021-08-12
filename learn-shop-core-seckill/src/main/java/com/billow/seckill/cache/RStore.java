package com.billow.seckill.cache;

public interface RStore {
    /**
     * 库存操作策列
     */
    enum Mode {
        DECRBY(-1, "扣减库存"),
        SET(0, "设置库存"),
        INCRBY(1, "增加库存"),
        ;
        private int flag;
        private String desc;

        Mode(int flag, String desc) {
            this.flag = flag;
            this.desc = desc;
        }

        public int getFlag() {
            return flag;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 扣减库存
     * 库存>0但不够扣时的策列
     */
    enum DecrMode {
        NOT_DECR(0, "不扣减"),
        DECR(1, "扣减剩余库存"),
        ;
        private int flag;
        private String desc;

        DecrMode(int flag, String desc) {
            this.flag = flag;
            this.desc = desc;
        }

        public int getFlag() {
            return flag;
        }

        public String getDesc() {
            return desc;
        }
    }
}
