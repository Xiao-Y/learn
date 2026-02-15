package com.billow.task.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskServiceAdapter {

    private final Map<String, TaskProcessService> processServiceMap = new ConcurrentHashMap<>();

    @Autowired
    public void setProcessServiceMap(List<TaskProcessService> taskProcessServices) {
        taskProcessServices.forEach(service -> processServiceMap.put(service.supportTaskType(), service));
    }

    public TaskProcessService getTaskProcessService(String taskType) {
        return processServiceMap.get(taskType);
    }
}