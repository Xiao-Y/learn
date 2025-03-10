package com.billow.excel.service;

import com.billow.excel.model.ExcelTask;

import java.util.List;

/**
 * Excel任务服务接口
 */
public interface ExcelTaskService {
    /**
     * 创建任务
     *
     * @param task 任务信息
     * @return 任务ID
     */
    String createTask(ExcelTask task);

    /**
     * 更新任务
     *
     * @param task 任务信息
     */
    void updateTask(ExcelTask task);

    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    ExcelTask getTask(String taskId);

    /**
     * 获取任务列表
     *
     * @param type   任务类型
     * @param status 任务状态
     * @return 任务列表
     */
    List<ExcelTask> listTasks(ExcelTask.TaskType type, ExcelTask.TaskStatus status);

    /**
     * 删除任务
     *
     * @param taskId 任务ID
     */
    void deleteTask(String taskId);
} 