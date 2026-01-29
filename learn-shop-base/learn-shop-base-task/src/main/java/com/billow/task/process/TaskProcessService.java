package com.billow.task.process;

import com.billow.taskcenter.entity.SysTaskGroup;
import com.billow.taskcenter.entity.SysTaskGroupDetail;

import java.util.List;

public interface TaskProcessService {

    List<SysTaskGroupDetail> splitTask(SysTaskGroup sysTaskGroup);

    SysTaskGroupDetail executeTask(SysTaskGroupDetail detail);

    String supportTaskType();
}