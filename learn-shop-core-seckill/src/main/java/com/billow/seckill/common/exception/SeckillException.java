package com.billow.seckill.common.exception;

import com.billow.tools.enums.ResCodeEnum;

/**
 * 签名异常
 *
 * @author liuyongtao
 * @create 2017-10-26 15:21
 */
public class SeckillException extends RuntimeException {

    public SeckillException() {
        super("秒杀异常...");
    }

    public SeckillException(ResCodeEnum resCodeEnum) {
        super(resCodeEnum.getStatusName());
    }
}
