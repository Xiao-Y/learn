package com.billow.excel.service;

import com.billow.excel.model.ExcelTask;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExcelTaskService {
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    String createTask(ExcelTask task);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    void updateTask(ExcelTask task);

    ExcelTask getTask(String taskId);

    List<ExcelTask> listTasks(ExcelTask.TaskType type, ExcelTask.TaskStatus status);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    void deleteTask(String taskId);
}
