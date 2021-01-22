package com.billow.seckill.pojo.vo;

import lombok.Data;

@Data
public class ExposerVo {

    /**
     * 是否开启秒杀
     */
    private boolean exposed;
    /**
     * 加密措施，避免用户通过抓包拿到秒杀地址
     */
    private String md5;
    /**
     * ID
     */
    private String seckillId;
    /**
     * 系统当前时间（毫秒）
     */
    private long now;
    /**
     * 秒杀开启时间
     */
    private long start;
    /**
     * 秒杀结束时间
     */
    private long end;

    public ExposerVo(boolean exposed, String md5, String seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public ExposerVo(boolean exposed, String seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public ExposerVo(boolean exposed, String seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
