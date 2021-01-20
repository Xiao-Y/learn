package com.billow.tools.exception;

/**
 * 邮件服务异常
 *
 * @author liuyongtao
 * @create 2017-10-26 15:21
 */
public class SimpleException extends RuntimeException {

    public SimpleException() {
        super("邮件服务异常...");
    }

    public SimpleException(String st) {
        super(st);
    }
}
