package com.billow.excel.controller;

import com.billow.excel.model.ExcelTask;
import com.billow.excel.service.ExcelTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel任务控制器
 */
@Slf4j
@RestController
@RequestMapping("/excel/task")
@RequiredArgsConstructor
public class ExcelTaskController {

    private final ExcelTaskService taskService;

    /**
     * 获取任务信息
     */
    @GetMapping("/{taskId}")
    public ExcelTask getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }

    /**
     * 获取任务列表
     */
    @GetMapping("/list")
    public List<ExcelTask> listTasks(
            @RequestParam(required = false) ExcelTask.TaskType type,
            @RequestParam(required = false) ExcelTask.TaskStatus status) {
        return taskService.listTasks(type, status);
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
    }

    /**
     * 下载任务文件
     *
     * @param taskId 任务ID
     * @return 文件资源
     */
    @GetMapping("/{taskId}/download")
    public ResponseEntity<Resource> downloadTaskFile(@PathVariable String taskId) {
        // 获取任务信息
        ExcelTask task = taskService.getTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在：" + taskId);
        }

        // 检查任务状态
        if (task.getStatus() != ExcelTask.TaskStatus.SUCCESS) {
            throw new IllegalStateException("任务未完成或执行失败：" + taskId);
        }

        // 检查文件是否存在
        String filePath = task.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("任务文件路径为空：" + taskId);
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalStateException("任务文件不存在：" + filePath);
        }

        // 创建文件资源
        Resource resource = new FileSystemResource(file);

        try {
            // 设置响应头
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + URLEncoder.encode(task.getFileName(), StandardCharsets.UTF_8.name()))
                    .body(resource);
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码失败", e);
            throw new RuntimeException("文件下载失败", e);
        }
    }
} 