package com.billow.excel.model;

import lombok.Data;

import java.util.List;

/**
 * Excel 导入结果
 */
@Data
public class ImportResult<T> {
    /**
     * 成功导入的数据
     */
    private List<T> successList;

    /**
     * 导入失败的数据
     */
    private List<ImportError> errorList;

    /**
     * 总记录数
     */
    private int total;

    /**
     * 成功记录数
     */
    private int successCount;

    /**
     * 失败记录数
     */
    private int errorCount;

    @Data
    public static class ImportError {
        /**
         * 行号
         */
        private int rowNum;

        /**
         * 错误信息
         */
        private String message;

        /**
         * 原始数据
         */
        private String rawData;
    }
} 