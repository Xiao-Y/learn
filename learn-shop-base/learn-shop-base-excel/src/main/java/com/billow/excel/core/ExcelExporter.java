package com.billow.excel.core;

import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Excel 导出接口
 */
public interface ExcelExporter {
    /**
     * 导出到文件
     *
     * @param dataList 数据列表
     * @param filePath 文件路径
     * @param <T>      数据类型
     */
    <T> void exportToFile(List<T> dataList, String filePath);

    /**
     * 导出到响应流
     *
     * @param dataList 数据列表
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    <T> void exportToResponse(List<T> dataList, HttpServletResponse response);

    /**
     * 导出到字节数组
     *
     * @param dataList 数据列表
     * @param <T>      数据类型
     * @return 字节数组
     */
    <T> byte[] exportToBytes(List<T> dataList);

    /**
     * 异步导出
     *
     * @param dataList 数据列表
     * @return 任务ID
     */
    <T> String exportAsync(List<T> dataList);

    /**
     * 导出Excel导入模板
     *
     * @param clazz    目标类型
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    <T> void exportTemplate(Class<T> clazz, HttpServletResponse response);
} 