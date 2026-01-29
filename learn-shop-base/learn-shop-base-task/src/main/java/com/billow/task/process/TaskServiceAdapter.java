package com.billow.task.process;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskServiceAdapter {
    private final Map<String, TaskProcessService> processServiceMap = new ConcurrentHashMap<>();

    @Resource
    public void setProcessServiceMap(java.util.List<TaskProcessService> taskProcessServices) {
        taskProcessServices.forEach(service -> processServiceMap.put(service.supportTaskType(), service));
    }

    public TaskProcessService getTaskProcessService(String taskType) {
        return processServiceMap.get(taskType);
    }
}