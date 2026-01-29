package com.billow.task.service;

import com.billow.taskcenter.entity.SysTaskGroup;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

public interface PosTaskGroupService extends IService<SysTaskGroup> {

    SysTaskGroup loadTask(SysTaskGroup taskGroup);

    void updateTaskProgress(Long taskId, Integer executeEndSize, Integer successSize);

    Double queryTaskProgress(Long taskId);

    Page<SysTaskGroup> queryTaskGroupList(Page<SysTaskGroup> page, String taskType, String status);

    void updateTaskSize(Long taskId, Integer taskSize);
}