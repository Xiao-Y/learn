package com.billow.promotion.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private Long seckillId;
    /**
     * 系统当前时间（毫秒）
     */
    private LocalDateTime now;
    /**
     * 请求链接失效时间（毫秒）
     */
    private Long expire;
    /**
     * 秒杀开启时间
     */
    private LocalTime start;
    /**
     * 秒杀结束时间
     */
    private LocalTime end;

    public ExposerVo() {
    }

    public ExposerVo(boolean exposed, String md5, Long seckillId, Long expire) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
        this.expire = expire;
    }

    public ExposerVo(boolean exposed, Long seckillId, LocalDateTime now, LocalTime start, LocalTime end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public ExposerVo(boolean exposed, Long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
