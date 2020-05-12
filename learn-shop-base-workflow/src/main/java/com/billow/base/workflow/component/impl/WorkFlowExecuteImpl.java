package com.billow.base.workflow.component.impl;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.extend.DeleteTaskCmd;
import com.billow.base.workflow.extend.SetFLowNodeAndGoCmd;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 工作流执行操作
 *
 * @author liuyongtao
 * @create 2019-08-25 10:38
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WorkFlowExecuteImpl implements WorkFlowExecute {

    private static final Logger logger = LoggerFactory.getLogger(WorkFlowExecuteImpl.class);

    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ManagementService managementService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Deployment deploy(String processName) {
        String fileName = processName + ".bpmn20.xml";
        String filePath = "processes/" + fileName;
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(filePath)
                .name(processName)
                .deploy();
        return deploy;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Deployment deploy(String resourceName, InputStream inputStream) {
        Deployment deploy = repositoryService.createDeployment()
                .addInputStream(resourceName, inputStream)
                .name(resourceName)
                .deploy();
        return deploy;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDeployment(String deploymentId, boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade);
    }

    @Override
    public void suspendProcess(String processDefinitionId) throws Exception {
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void suspendProcessCascade(String processDefinitionId) throws Exception {
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
    }

    @Override
    public void activateProcess(String processDefinitionId) throws Exception {
        repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void activateProcessCascade(String processDefinitionId) throws Exception {
        repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProcessInstanceVo startProcessInstance(String operator, String key, String businessKey, Map<String, Object> variables) {
        return this.startProcessInstance(operator, "key", key, businessKey, variables);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProcessInstanceVo startProcessInstance(String operator, String processType, String pk, String businessKey, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        // 设置启动人
        identityService.setAuthenticatedUserId(operator);
        ProcessInstance processInstance = null;
        if ("id".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceById(pk, businessKey, variables);
        } else if ("key".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceByKey(pk, businessKey, variables);
        }
        ProcessInstanceVo ex = new ProcessInstanceVo();
        ex.setProcessInstanceId(processInstance.getProcessInstanceId());
        ex.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        return ex;
    }

    @Override
    public void commitProcess(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitProcess(String taskId, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        taskService.complete(taskId, variables);
    }

    @Override
    public void addCandidateGroup(String taskId, String groupId) {
        taskService.addCandidateGroup(taskId, groupId);
    }

    @Override
    public void setAssignee(String procInstId, String userId, String taskCode) {
        if (StringUtils.isEmpty(userId)) {
            return;
        }
        List<Task> tasks = workFlowQuery.queryTasksByProcessId(procInstId);
        tasks.stream().forEach(fe -> {
            // 如果指定任务code ,则只设置指定的。否则设置所有的
            if (StringUtils.isNotEmpty(taskCode)) {
                if (Objects.equals(taskCode, fe.getTaskDefinitionKey())) {
                    this.setAssignee(fe.getId(), userId);
                }
            } else {
                this.setAssignee(fe.getId(), userId);
            }
        });
    }

    @Override
    public void setAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    @Override
    public void claim(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    @Override
    public void unclaim(String taskId) {
        taskService.unclaim(taskId);
    }

    @Override
    public void setAuthUser(String userId) {
        identityService.setAuthenticatedUserId(userId);
    }

    @Override
    public void rollBackTask(String taskId, String userId, String reason, String groupId, int backNum) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new Exception("流程未启动或已执行完成，无法撤回");
        }
        String applyUserId = workFlowQuery.queryCurrentApplyUserId(task.getProcessInstanceId());
        // 上一个task
        HistoricTaskInstance preTask = workFlowQuery.queryApplyUserTask(task.getProcessInstanceId(), backNum);
        if (preTask == null || preTask.getId() == null) {
            return;
        }
        String processDefinitionId = preTask.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //变量
        HistoricActivityInstance myActivity = workFlowQuery.queryCurrentApplyActivity(task.getExecutionId(), preTask.getId());
        String myActivityId = myActivity.getActivityId();

        //得到流程节点
        FlowNode targetNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);
        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        logger.warn("------->> activityId:" + activityId);
        FlowNode sourceNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);
        // 记录原来方向
        List<SequenceFlow> oriSequenceFlowList = new ArrayList<>();
        oriSequenceFlowList.addAll(sourceNode.getOutgoingFlows());
        //清理活动方向
        sourceNode.getOutgoingFlows().clear();
        //建立新方向
        taskService.setVariableLocal(task.getId(), "reason", reason);
        taskService.setVariableLocal(task.getId(), "auditGroupId", groupId);
        buildNewFlowNode(task, applyUserId, targetNode, sourceNode, userId, reason);
        //记录原活动方向，恢复原方向
        sourceNode.setOutgoingFlows(oriSequenceFlowList);
        // 设置任务候选人
        this.setAssignee(task.getProcessInstanceId(),preTask.getAssignee(),null);
    }

    @Override
    public void addComment(String userCode, String procInstId, String taskId, String comment) {
        identityService.setAuthenticatedUserId(userCode);
        taskService.addComment(taskId, procInstId, comment);
    }

    @Override
    public void jump(String taskId, String targetFlowElementId) {
        // 当前任务
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (currentTask == null) {
            throw new ActivitiException("当前任务不存在或已被办理完成，回退失败！");
        }
        // 获取流程定义
        Process process = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        // 获取目标节点定义
        FlowNode targetNode = (FlowNode) process.getFlowElement(targetFlowElementId);
        // 删除当前运行任务
        String executionEntityId = managementService.executeCommand(new DeleteTaskCmd(currentTask.getId()));
        // 流程执行到来源节点
        managementService.executeCommand(new SetFLowNodeAndGoCmd(targetNode, executionEntityId));
    }

    /**
     * 建立新方向
     *
     * @param task
     * @param applyUserId
     * @param targetNode
     * @param sourceNode
     */
    private void buildNewFlowNode(Task task, String applyUserId, FlowNode targetNode, FlowNode sourceNode, String userId, String reason) {
        List<SequenceFlow> newSequenceFlowList = new ArrayList<>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(sourceNode);
        newSequenceFlow.setTargetFlowElement(targetNode);
        newSequenceFlowList.add(0, newSequenceFlow);
        sourceNode.setOutgoingFlows(newSequenceFlowList);
        identityService.setAuthenticatedUserId(applyUserId);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "驳回");
        //添加代理
        this.setAssignee(task.getId(), userId);
        //完成任务
        task.setCategory("refuse");
        taskService.setVariableLocal(task.getId(), "reson", reason);
        commitProcess(task.getId());
        Task t =
                taskService.createTaskQuery().active().processInstanceId(task.getProcessInstanceId()).singleResult();
        taskService.setVariableLocal(t.getId(), "reason", reason);
    }
}
