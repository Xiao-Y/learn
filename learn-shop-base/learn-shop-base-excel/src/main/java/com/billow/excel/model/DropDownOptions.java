package com.billow.excel.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Excel下拉列表配置
 */
@Data
@Accessors(chain = true)
public class DropDownOptions {
    /**
     * 列索引
     */
    private int columnIndex;
    
    /**
     * 起始行（包含）
     */
    private int firstRow;
    
    /**
     * 结束行（包含）
     */
    private int lastRow;
    
    /**
     * 下拉选项
     */
    private List<String> options;
} 