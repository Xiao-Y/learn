package com.billow.excel.model;

import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.annotation.ExcelSheet;
import lombok.Data;

/**
 * 导入时返回的异常信息
 *
 * @author 千面
 * @date 2025/6/9 18:12
 */
@Data
@ExcelSheet(name = "导入异常信息")
public class ExportErrorResult {

    /**
     * 行号
     *
     * @author 千面
     */
    @ExcelColumn(title = "行号")
    private int index;

    /**
     * 错误原因
     *
     * @author 千面
     */
    @ExcelColumn(title = "错误原因")
    private String errorMsg;
}