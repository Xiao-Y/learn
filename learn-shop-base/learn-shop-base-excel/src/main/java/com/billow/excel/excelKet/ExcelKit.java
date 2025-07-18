package com.billow.excel.excelKet;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.billow.excel.annotation.ExcelSheet;
import com.billow.excel.config.ExcelProperties;
import com.billow.excel.generator.NumUtil;
import com.billow.excel.model.ExcelTask;
import com.billow.excel.model.ExportErrorResult;
import com.billow.excel.model.ExportPageResult;
import com.billow.excel.service.ExcelTaskService;
import com.billow.tools.utlis.SpringContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * 默认Excel导出实现
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelKit {

    private final ExcelProperties excelProperties;
    private final ExcelCore excelCore;
    private final ExcelTaskService excelTaskService;


    public static ExcelKit getInstance() {
        return SpringContextUtil.getBean(ExcelKit.class);
    }

    private ExcelCore getExcelCore() {
        return excelCore;
    }

    /**
     * 异步导出
     *
     * @param dataList 数据列表
     * @return 任务ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <T> String exportAsync(Class<T> clazz, List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("导出数据为空");
        }

        // 创建任务记录
        ExcelTask task = this.createTask(clazz, dataList.size());

        // 异步执行导出
        CompletableFuture.runAsync(() -> {
            try {
                excelCore.exportToFile(dataList, task.getFilePath() + task.getFileName());

                // 更新任务状态为成功
                task.setStatus(ExcelTask.TaskStatus.COMPLETED)
                        .setSuccessCount(dataList.size());
            } catch (Exception e) {
                log.error("异步导出Excel失败", e);
                // 更新任务状态为失败
                task.setStatus(ExcelTask.TaskStatus.FAILED)
                        .setErrorCount(dataList.size())
                        .setErrorMessage(e.getMessage());
            } finally {
                task.setUpdateTime(new Date());
                String formatDuration = DateUtil.formatBetween(task.getUpdateTime(), task.getCreateTime());
                task.setTimeDifference(formatDuration);
                excelTaskService.updateTask(task);
            }
        });
        return task.getTaskId();
    }


    /**
     * 异步基于 count 分页导出
     * <p>
     * //假设已经有 ExcelCore 实例
     * <p>
     * ExcelCore excelCore = new ExcelCore(dictService);
     * <p>
     * String filePath = "/path/to/";
     * <p>
     * String fileName = "exported_file.xlsx";
     * <p>
     * int pageSize = 100;
     * <p>
     * int totalCount = yourService.getTotalCount(); // 从数据库获取数据总数量
     * <p>
     * BiFunction<Integer, Integer, List<YourDataClass>> dataFetcher = (page, size) -> {
     * <p>
     * return yourService.getPageData(page, size); // 调用数据库查询方法获取分页数据
     * <p>
     * };
     * <p>
     * excelCore.exportToFileWithPage(YourDataClass, pageSize, totalCount, dataFetcher);
     *
     * @param pageSize    每页大小
     * @param totalCount  总条数
     * @param dataFetcher 数据获取器
     * @param //
     * @return 任务ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <T> String exportToFileWithPage(Class<T> clazz, int pageSize, int totalCount,
                                           BiFunction<Integer, Integer, List<T>> dataFetcher) {
        // 创建任务记录
        ExcelTask task = this.createTask(clazz, totalCount);

        // 异步执行导出
        CompletableFuture.runAsync(() -> {
            try {
                int count = excelCore.exportToFileWithPage(task.getFilePath() + task.getFileName(), pageSize, totalCount, dataFetcher);
                // 更新任务状态为成功
                task.setStatus(ExcelTask.TaskStatus.COMPLETED)
                        .setSuccessCount(count);
            } catch (Exception e) {
                log.error("异步基于 count 分页导出Excel失败", e);
                // 更新任务状态为失败
                task.setStatus(ExcelTask.TaskStatus.FAILED)
                        .setErrorCount(totalCount)
                        .setErrorMessage(e.getMessage());
            } finally {
                task.setUpdateTime(new Date());
                String formatDuration = DateUtil.formatBetween(task.getUpdateTime(), task.getCreateTime());
                task.setTimeDifference(formatDuration);
                excelTaskService.updateTask(task);
            }
        });
        return task.getTaskId();
    }

    /**
     * 异步基于游标分页导出
     * <p>
     * //假设已经有 ExcelCore 实例
     * <p>
     * ExcelCore excelCore = new ExcelCore(dictService);
     * <p>
     * int pageSize = 100;
     * <p>
     * Q泛型 initialQueryParam = 0; // 从id为0的数据开始查询
     * <p>
     * BiFunction<Q泛型, Integer, ExportPageResult<Q泛型, YourDataClass>> dataFetcher = (lastQueryParam, size) -> {
     * <p>
     * // 类似这种查询：select * from your_table where id > ? limit ?;
     * <p>
     * List<YourDataClass> dataList = yourService.getPageData(lastQueryParam, size);
     * <p>
     * ExportPageResult<Q泛型, YourDataClass> result = new ExportPageResult<>();
     * <p>
     * setDataList(dataList);
     * <p>
     * setLastQueryParam(dataList.get(dataList.size() - 1).getId());
     * <p>
     * return result; // 调用数据库查询方法获取分页数据
     * <p>
     * excelCore.exportToFileWithPage(YourDataClass, pageSize, initialQueryParam, dataFetcher);
     *
     * @param pageSize          每页大小
     * @param initialQueryParam 初始查询条件，用于第一页查询的起始点。
     * @param dataFetcher       数据获取器
     * @param //
     * @return 任务ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <Q, T> String exportToFileWithRange(Class<T> clazz, int pageSize, Q initialQueryParam,
                                               BiFunction<Q, Integer, ExportPageResult<Q, T>> dataFetcher) {
        // 创建任务记录
        ExcelTask task = this.createTask(clazz, 0);

        // 异步执行导出
        CompletableFuture.runAsync(() -> {
            try {
                int count = excelCore.exportToFileWithRange(task.getFilePath() + task.getFileName(), pageSize, initialQueryParam, dataFetcher);
                // 更新任务状态为成功
                task.setStatus(ExcelTask.TaskStatus.COMPLETED)
                        .setSuccessCount(count);
            } catch (Exception e) {
                log.error("异步基于游标分页导出Excel失败", e);
                // 更新任务状态为失败
                task.setStatus(ExcelTask.TaskStatus.FAILED)
                        .setErrorMessage(e.getMessage());
            } finally {
                task.setUpdateTime(new Date());
                String formatDuration = DateUtil.formatBetween(task.getUpdateTime(), task.getCreateTime());
                task.setTimeDifference(formatDuration);
                excelTaskService.updateTask(task);
            }
        });
        return task.getTaskId();
    }

    /**
     * 导出Excel导入模板
     *
     * @param clazz    目标类型
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    public <T> void exportTemplate(Class<T> clazz, HttpServletResponse response) {
        excelCore.exportTemplate(response, clazz, this.getFileName(clazz));
    }

    /**
     * 异常错误信息导出
     *
     * @param errorList 错误信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String exportErrorResult(List<ExportErrorResult> errorList) throws Exception {
        return this.exportAsync(ExportErrorResult.class, errorList);
    }

    /**
     * 从MultipartFile导入（不可异步，会有异常）
     *
     * @param file  上传的文件
     * @param clazz 目标类型
     * @param <T>   数据类型
     * @return 导入结果Future
     */
    public <T> List<T> importFromMultipartFile(MultipartFile file, Class<T> clazz) {
        return excelCore.importFromMultipartFile(file, clazz);
    }

    /**
     * 从文件导入
     *
     * @param filePath 文件路径
     * @param clazz    目标类型
     * @param <T>      数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromFile(String filePath, Class<T> clazz) {
        return excelCore.importFromFile(filePath, clazz);
    }

    /**
     * 从输入流导入（可异步）
     *
     * @param inputStream 输入流
     * @param clazz       目标类型
     * @param <T>         数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromStream(InputStream inputStream, Class<T> clazz) {
        return excelCore.importFromStream(inputStream, clazz);
    }

    /**
     * 创建任务记录
     *
     * @param clazz
     * @param total
     * @param <T>
     * @return
     */
    private <T> ExcelTask createTask(Class<T> clazz, Integer total) {
        // 创建任务记录
        String taskId = NumUtil.makeNum("EX");

        // 创建任务记录
        ExcelTask task = new ExcelTask()
                .setTaskId(taskId)
                .setType(ExcelTask.TaskType.EXPORT)
                .setFilePath(this.getFileBasePath())
                .setFileName(this.getFileName(clazz))
                .setStatus(ExcelTask.TaskStatus.PROCESSING)
                .setTotal(total)
                .setCreateTime(new Date())
                .setSuccessCount(0)
                .setErrorCount(0);
        excelTaskService.createTask(task);
        return task;
    }

    private <T> String getFileName(Class<?> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String fileName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();
        return fileName + "_" + System.currentTimeMillis() + ".xlsx";
    }

    private String getFileBasePath() {
        String fiilePath = Optional.ofNullable(excelProperties.getFileBasePath())
                .orElse(System.getProperty("java.io.tmpdir"));
        // 是否以/ 结尾，如果不是则 + "/"
        if (!fiilePath.endsWith("/")) {
            fiilePath = fiilePath + "/";
        }
        // 判断文件夹是否存在，如果不存在则创建
        if (!FileUtil.exist(fiilePath)) {
            FileUtil.mkdir(fiilePath);
        }
        return fiilePath;
    }
}
