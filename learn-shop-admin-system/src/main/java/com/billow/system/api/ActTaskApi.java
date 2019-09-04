package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import com.billow.base.workflow.vo.TaskVo;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.system.service.ApplyInfoService;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private ApplyInfoService applyInfoService;

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
        String currentUserCode = userTools.getCurrentUserCode();
        return workFlowExecute.startProcessInstance(currentUserCode, processType, pk, businessKey, variables);
    }

    @ApiOperation(value = "查询个人任务列表")
    @PostMapping("/queryMyTaskList")
    public Page queryMyTaskList(@RequestBody ApplyInfoVo applyInfoVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        applyInfoVo.setAssignee(currentUserCode);
        Page applyInfoVoPage = applyInfoService.queryMyTaskList(applyInfoVo, applyInfoVo.getOffset(), applyInfoVo.getPageSize());
        return applyInfoVoPage;
    }

    @ApiOperation(value = "查询个人任务数量")
    @GetMapping("/queryAssigneeTaskCount")
    public long queryAssigneeTaskCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        TaskVo taskVo = new TaskVo();
        taskVo.setAssignee(currentUserCode);
        long count = workFlowQuery.queryAssigneeTaskCount(taskVo);
        return count;
    }

    @ApiOperation(value = "我发起的流程（所有的）")
    @GetMapping("/myStartProdeCount")
    public long myStartProdeCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeAllCount(currentUserCode);
        return count;
    }

    @ApiOperation(value = "审核中的的流程（运行中的）")
    @GetMapping("/auditProgressProdeCount")
    public long auditProgressProdeCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeActiveCount(currentUserCode);
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

    @ApiOperation(value = "查看活动的流程图（显示运行轨迹）")
    @GetMapping("/viewExecutionImgById/{executionId}")
    public void viewDeployImgById(@PathVariable String executionId, HttpServletResponse response) throws Exception {
        workFlowQuery.genActiveProccessImage(executionId, response);
    }

}
