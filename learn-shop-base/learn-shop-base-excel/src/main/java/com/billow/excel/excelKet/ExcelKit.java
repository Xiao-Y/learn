package com.billow.excel.excelKet;

import cn.hutool.core.date.DateUtil;
import com.billow.excel.annotation.ExcelSheet;
import com.billow.excel.generator.NumUtil;
import com.billow.excel.model.ExcelTask;
import com.billow.excel.service.ExcelTaskService;
import com.billow.tools.utlis.SpringContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 默认Excel导出实现
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelKit {

    private final ExcelCore excelCore;
    private final ExcelTaskService excelTaskService;


    public static ExcelKit getInstance() {
        return SpringContextUtil.getBean(ExcelKit.class);
    }

    /**
     * 异步导出
     *
     * @param dataList 数据列表
     * @return 任务ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <T> String exportAsync(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("导出数据为空");
        }
        // 创建任务记录
        String taskId = NumUtil.makeNum("EX");
        // String filePath = System.getProperty("java.io.tmpdir") + "/" + taskId + ".xlsx";
        String fileName = this.getFileName(dataList);
        String filePath = "D:/" + fileName;

        // 创建任务记录
        ExcelTask task = new ExcelTask()
                .setTaskId(taskId)
                .setType(ExcelTask.TaskType.EXPORT)
                .setFileName(fileName)
                .setStatus(ExcelTask.TaskStatus.PROCESSING)
                .setTotal(dataList.size())
                .setCreateTime(new Date())
                .setSuccessCount(0)
                .setErrorCount(0);
        excelTaskService.createTask(task);

        // 异步执行导出
        CompletableFuture.runAsync(() -> {
            try {
                excelCore.exportToFile(dataList, filePath);

                // 更新任务状态为成功
                task.setStatus(ExcelTask.TaskStatus.COMPLETED)
                        .setSuccessCount(dataList.size())
                        .setFilePath(filePath);
                Thread.sleep(10500);
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
        return taskId;
    }


    /**
     * 导出Excel导入模板
     *
     * @param clazz    目标类型
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    public <T> void exportTemplate(Class<T> clazz, HttpServletResponse response) {
        excelCore.exportTemplate(response, clazz, this.getTemplateFileName(clazz));
    }


    /**
     * 从MultipartFile导入
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

    private <T> String getTemplateFileName(Class<T> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String fileName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();
        return fileName + "_导入模板_" + System.currentTimeMillis() + ".xlsx";
    }

    private <T> String getFileName(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return "export_" + System.currentTimeMillis() + ".xlsx";
        }
        Class<?> clazz = dataList.get(0).getClass();
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String fileName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();
        return fileName + "_" + System.currentTimeMillis() + ".xlsx";
    }

}
