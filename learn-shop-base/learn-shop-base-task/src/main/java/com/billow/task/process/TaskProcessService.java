package com.billow.task.process;


import com.billow.task.entity.TaskGroup;
import com.billow.task.entity.TaskGroupDetail;

import java.util.List;

public interface TaskProcessService {

    List<TaskGroupDetail> splitTask(TaskGroup taskGroup);

    TaskGroupDetail executeTask(TaskGroupDetail detail);

    String supportTaskType();
}