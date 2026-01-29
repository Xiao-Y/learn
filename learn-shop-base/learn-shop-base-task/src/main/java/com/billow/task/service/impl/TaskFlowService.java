package com.billow.task.service.impl;

import com.billow.task.entity.TaskGroup;
import com.billow.task.entity.TaskGroupDetail;
import com.billow.task.process.TaskProcessService;
import com.billow.task.process.TaskServiceAdapter;
import com.billow.task.service.PosTaskGroupDetailService;
import com.billow.task.service.PosTaskGroupService;
import com.billow.task.util.TaskStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskFlowService {
    private final PosTaskGroupService taskGroupService;
    private final PosTaskGroupDetailService taskDetailService;
    private final TaskServiceAdapter taskServiceAdapter;
    private final RabbitTemplate rabbitTemplate;

    @Value("${task.center.mq.task-split-queue}")
    private String taskSplitQueue;

    @Value("${task.center.mq.task-execute-queue}")
    private String taskExecuteQueue;

    public TaskGroup startTask(TaskGroup taskGroup) {
        if (taskGroup == null || StringUtils.isBlank(taskGroup.getType()) || StringUtils.isBlank(taskGroup.getTaskParam())) {
            throw new RuntimeException("任务组参数不完整：type和taskParam不能为空");
        }

        TaskGroup savedGroup = taskGroupService.loadTask(taskGroup);

        try {
            rabbitTemplate.convertAndSend(taskSplitQueue, savedGroup.getId());
            log.info("任务加载成功，已发送拆分MQ消息，groupNo：{}", savedGroup.getGroupNo());
        } catch (Exception e) {
            log.error("发送任务拆分MQ消息失败，groupNo：{}", savedGroup.getGroupNo(), e);
            throw new RuntimeException("发送拆分MQ消息失败：" + e.getMessage());
        }

        return savedGroup;
    }

    public void splitTask(Long taskId) {
        log.info("开始执行任务拆分，taskId：{}", taskId);

        TaskGroup taskGroup = taskGroupService.getById(taskId);
        if (taskGroup == null) {
            throw new RuntimeException("任务组不存在，无法拆分：" + taskId);
        }

        TaskProcessService processService = taskServiceAdapter.getTaskProcessService(taskGroup.getType());
        if (processService == null) {
            throw new RuntimeException("未找到[" + taskGroup.getType() + "]类型的任务处理实现类");
        }

        List<TaskGroupDetail> detailList = processService.splitTask(taskGroup);
        if (detailList == null || detailList.isEmpty()) {
            log.warn("任务拆分结果为空，taskId：{}", taskId);
            return;
        }

        taskDetailService.saveBatch(detailList);
        taskGroupService.updateTaskSize(taskId, detailList.size());

        for (TaskGroupDetail detail : detailList) {
            try {
                rabbitTemplate.convertAndSend(taskExecuteQueue, detail.getId());
            } catch (Exception e) {
                log.error("发送子任务执行MQ消息失败，taskId：{}", detail.getTaskId(), e);
            }
        }

        log.info("任务拆分完成，taskId：{}，拆分出{}个子任务", taskId, detailList.size());
    }

    public void executeTask(Long taskDetailId) {
        log.info("开始执行子任务，taskDetailId：{}", taskDetailId);

        TaskGroupDetail detail = taskDetailService.getById(taskDetailId);
        if (detail == null) {
            throw new RuntimeException("子任务不存在，无法执行：" + taskDetailId);
        }

        Long taskId = detail.getTaskId();
        String groupNo = detail.getGroupNo();

        TaskGroup taskGroup = taskGroupService.getById(taskId);
        if (taskGroup == null) {
            throw new RuntimeException("任务组不存在，无法执行子任务：" + taskId);
        }

        TaskProcessService processService = taskServiceAdapter.getTaskProcessService(taskGroup.getType());
        if (processService == null) {
            throw new RuntimeException("未找到[" + taskGroup.getType() + "]类型的任务处理实现类");
        }

        TaskGroupDetail executedDetail = processService.executeTask(detail);
        taskDetailService.updateById(executedDetail);
        Map<String, Integer> counted = taskDetailService.countFinishedByGroupNo(taskId);
        int successSize = counted.getOrDefault(TaskStatusEnum.S.name(), 0);
        int failSize = counted.getOrDefault(TaskStatusEnum.F.name(), 0);
        int executeEndSize = failSize + successSize;
        taskGroupService.updateTaskProgress(taskId, executeEndSize, successSize);

        log.info("子任务执行完成，taskDetailId：{}，执行状态：{}", taskDetailId, executedDetail.getExecuteStatus());
    }
}