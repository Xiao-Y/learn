package com.billow.tools.exception;

import com.billow.tools.enums.ResCodeEnum;

/**
 * Created by billow.
 *
 * @author: bilow
 * @version: 1.0
 * @date: 2019/6/2 20:20
 * @apiNote: 全局异常
 */
public class GlobalException extends RuntimeException {

    private ResCodeEnum resCodeEnum;

    public GlobalException(ResCodeEnum resCodeEnum) {
        super(resCodeEnum.getStatusName());
        this.resCodeEnum = resCodeEnum;
    }

    public ResCodeEnum getResCodeEnum() {
        return resCodeEnum;
    }
}
