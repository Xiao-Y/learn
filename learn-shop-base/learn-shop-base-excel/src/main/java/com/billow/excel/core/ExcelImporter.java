package com.billow.excel.core;

import com.billow.excel.model.ImportResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Excel 导入接口
 */
public interface ExcelImporter {
    /**
     * 从文件导入
     *
     * @param filePath 文件路径
     * @param clazz    目标类型
     * @param <T>      数据类型
     * @return 导入的数据列表
     */
    <T> List<T> importFromFile(String filePath, Class<T> clazz);

    /**
     * 从输入流导入
     *
     * @param inputStream 输入流
     * @param clazz       目标类型
     * @param <T>         数据类型
     * @return 导入的数据列表
     */
    <T> List<T> importFromStream(InputStream inputStream, Class<T> clazz);

    /**
     * 从MultipartFile导入
     *
     * @param file  上传的文件
     * @param clazz 目标类型
     * @param <T>   数据类型
     * @return 导入的数据列表
     */
    <T> List<T> importFromMultipartFile(MultipartFile file, Class<T> clazz);

    /**
     * 异步导入
     *
     * @param file  上传的文件
     * @param clazz 目标类型
     * @param <T>   数据类型
     * @return 导入结果Future
     */
    <T> Future<ImportResult<T>> importAsync(MultipartFile file, Class<T> clazz);
} 