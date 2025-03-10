package com.billow.excel.converter;

/**
 * Excel数据转换器接口
 *
 * @param <S> 源数据类型
 * @param <T> 目标数据类型
 */
public interface ExcelConverter<S, T> {
    
    /**
     * 将源数据转换为目标数据（导入时使用）
     *
     * @param value 源数据
     * @return 目标数据
     */
    T convert(S value);
    
    /**
     * 将目标数据转换为源数据（导出时使用）
     *
     * @param value 目标数据
     * @return 源数据
     */
    S revert(T value);
} 