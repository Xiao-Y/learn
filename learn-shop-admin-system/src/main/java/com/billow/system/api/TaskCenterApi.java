package com.billow.system.api;

import com.billow.task.entity.TaskGroup;
import com.billow.task.entity.TaskGroupDetail;
import com.billow.task.service.PosTaskGroupDetailService;
import com.billow.task.service.PosTaskGroupService;
import com.billow.task.service.impl.TaskFlowService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskCenterApi {
    private final TaskFlowService taskFlowService;
    private final PosTaskGroupService taskGroupService;
    private final PosTaskGroupDetailService taskDetailService;

    @PostMapping("/start")
    public TaskGroup startTask(@RequestBody TaskGroup taskGroup) {
        return taskFlowService.startTask(taskGroup);
    }

    @GetMapping("/progress/{taskId}")
    public Map<String, Object> queryTaskProgress(@PathVariable Long taskId) {
        Double progress = taskGroupService.queryTaskProgress(taskId);
        TaskGroup taskGroup = taskGroupService.getById(taskId);
        return Map.of(
                "groupNo", taskGroup.getGroupNo(),
                "progress", progress + "%",
                "totalTaskSize", taskGroup.getTaskSize(),
                "finishedSize", taskGroup.getExecuteEndSize(),
                "successSize", taskGroup.getSuccessSize(),
                "executeStatus", taskGroup.getExecuteStatus(),
                "msg", taskGroup.getMsg()
        );
    }

    @GetMapping("/group/list")
    public Page<TaskGroup> queryTaskGroupList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String taskType,
            @RequestParam(required = false) String status) {
        Page<TaskGroup> page = new Page<>(pageNum, pageSize);
        return taskGroupService.queryTaskGroupList(page, taskType, status);
    }

    @GetMapping("/detail/list")
    public Page<TaskGroupDetail> queryTaskDetailList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam String groupNo) {
        Page<TaskGroupDetail> page = new Page<>(pageNum, pageSize);
        return taskDetailService.queryTaskDetailList(page, groupNo);
    }

    @PostMapping("/detail/retry/{taskDetailId}")
    public String retryTaskDetail(@PathVariable String taskDetailId) {
        boolean success = taskDetailService.retryTaskDetail(taskDetailId);
        if (success) {
            return "子任务重试请求提交成功";
        } else {
            return "子任务重试请求提交失败";
        }
    }
}