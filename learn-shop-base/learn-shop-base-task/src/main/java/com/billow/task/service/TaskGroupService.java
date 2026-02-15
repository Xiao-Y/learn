package com.billow.task.service;

import com.billow.task.entity.TaskGroup;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

public interface TaskGroupService extends IService<TaskGroup> {

    TaskGroup loadTask(TaskGroup taskGroup);

    void updateTaskProgress(Long taskId, Integer executeEndSize, Integer successSize);

    Double queryTaskProgress(Long taskId);

    Page<TaskGroup> queryTaskGroupList(Page<TaskGroup> page, String taskType, String status);

    void updateTaskSize(Long taskId, Integer taskSize);
}