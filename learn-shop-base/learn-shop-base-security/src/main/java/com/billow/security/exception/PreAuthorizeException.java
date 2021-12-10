package com.billow.security.exception;

/**
 * 权限异常
 * 
 * @author billow
 */
public class PreAuthorizeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public PreAuthorizeException()
    {
        super("没有权限，请联系管理员");
    }
}
