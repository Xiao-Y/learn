package com.billow.excel.validator;

/**
 * Excel数据校验器接口
 *
 * @param <T> 待校验的数据类型
 */
public interface ExcelValidator<T> {
    
    /**
     * 校验数据
     *
     * @param value 待校验的值
     * @return 校验是否通过
     */
    boolean validate(T value);
    
    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getErrorMessage();
} 