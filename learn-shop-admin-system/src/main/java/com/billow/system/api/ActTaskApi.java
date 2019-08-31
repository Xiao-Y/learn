package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import com.billow.base.workflow.vo.TaskVo;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 工作流任务API
 *
 * @author liuyongtao
 * @create 2019-08-24 15:52
 */
@Slf4j
@RestController
@RequestMapping("/actTaskApi")
@Api(value = "工作流任务API")
public class ActTaskApi {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private UserTools userTools;

    /**
     * 启动流程实例
     *
     * @param processType 流程启动类型，id 或者 key(与 pk 对应)
     * @param pk          processDefinitionId 或者 processDefinitionKey
     * @param businessKey 业务id
     * @param variables   启动参数
     * @return org.activiti.engine.runtime.ProcessInstance
     * @author billow
     * @date 2019/8/24 15:46
     */
    @ApiOperation(value = "启动流程实例")
    @PostMapping("/startProcessInstance/{processType}/{pk}/{businessKey}")
    public ProcessInstanceVo startProcessInstance(@PathVariable("processType") String processType,
                                                  @PathVariable("pk") String pk,
                                                  @PathVariable("businessKey") String businessKey,
                                                  @RequestBody Map<String, Object> variables) {
        return workFlowExecute.startProcessInstance(processType, pk, businessKey, variables);
    }

    @ApiOperation(value = "查询个人任务列表")
    @PostMapping("/queryOwnerTaskList")
    public Page<TaskVo> queryOwnerTaskList(@RequestBody TaskVo taskVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        taskVo.setOwner(currentUserCode);
        Page<TaskVo> taskVos = workFlowQuery.queryTaskList(taskVo, taskVo.getOffset(), taskVo.getPageSize());
        return taskVos;
    }

    @ApiOperation(value = "查询个人任务数量")
    @PostMapping("/queryOwnerTaskCount")
    public long queryOwnerTaskCount() {
        TaskVo taskVo = new TaskVo();
        String currentUserCode = userTools.getCurrentUserCode();
        taskVo.setOwner(currentUserCode);
        long count = workFlowQuery.queryOwnerTaskCount(taskVo);
        return count;
    }

    @ApiOperation(value = "认领任务")
    @PostMapping("/claimTask/{taskId}")
    public void claimTask(@PathVariable String taskId) {
        String currentUserCode = userTools.getCurrentUserCode();
        workFlowExecute.claim(taskId, currentUserCode);
    }

    @ApiOperation(value = "放弃认领任务")
    @PostMapping("/unclaimTask/{taskId}")
    public void unclaimTask(@PathVariable String taskId) {
        workFlowExecute.unclaim(taskId);
    }

    @ApiOperation(value = "查询任务列表")
    @PostMapping("/queryTaskList")
    public Page<TaskVo> queryTaskList(@RequestBody TaskVo taskVo) {
        Page<TaskVo> taskVos = workFlowQuery.queryTaskList(taskVo, taskVo.getOffset(), taskVo.getPageSize());
        return taskVos;
    }

    @ApiOperation(value = "提交任务")
    @PostMapping("/commitProcess/{taskId}")
    public void commitProcess(@PathVariable("taskId") String taskId,
                              @RequestBody Map<String, Object> variables) {
        workFlowExecute.commitProcess(taskId, variables);
    }
}
