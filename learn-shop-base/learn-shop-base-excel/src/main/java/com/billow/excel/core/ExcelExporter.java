package com.billow.excel.core;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.Future;

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
     * @param dataList  数据列表
     * @param response  HTTP响应对象
     * @param <T>       数据类型
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
     * @param taskId   任务ID
     * @param <T>      数据类型
     * @return 导出结果Future
     */
    <T> Future<String> exportAsync(List<T> dataList, String taskId);

    /**
     * 导出Excel导入模板
     *
     * @param clazz    目标类型
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    <T> void exportTemplate(Class<T> clazz, HttpServletResponse response);
} 