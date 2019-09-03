package com.billow.base.workflow.component.impl;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
        String applyUserId = getCurrentApplyUserId(task.getProcessInstanceId());
        // 上一个task
        HistoricTaskInstance preTask = getApplyUserTask(task.getProcessInstanceId(), backNum);
        if (preTask == null || preTask.getId() == null) {
            return;
        }
        String processDefinitionId = preTask.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //变量
        HistoricActivityInstance myActivity = getCurrentApplyActivity(task.getExecutionId(), preTask.getId());
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
        setAssigneeUser(task, preTask.getAssignee());
    }

    /**
     * 新任务设置候选人
     *
     * @param task
     * @param applyUserId
     */
    private void setAssigneeUser(Task task, String applyUserId) {
        Task newTask = workFlowQuery.queryTaskByProcessId(task.getProcessInstanceId());
        taskService.setAssignee(newTask.getId(), applyUserId);
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
        taskService.setAssignee(task.getId(), userId);
        //完成任务
        task.setCategory("refuse");
        taskService.setVariableLocal(task.getId(), "reson", reason);
        commitProcess(task.getId());
        Task t =
                taskService.createTaskQuery().active().processInstanceId(task.getProcessInstanceId()).singleResult();
        taskService.setVariableLocal(t.getId(), "reason", reason);
    }

    /**
     * 获取当前流程的申请人
     *
     * @param processId
     * @return
     */
    private String getCurrentApplyUserId(String processId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId)
                .singleResult();
        String applyUserId = processInstance.getStartUserId();
        return applyUserId;
    }

    /**
     * 获取当前任务的历史活动实例
     *
     * @param excutionId 任务执行id
     * @param taskId     任务id
     * @return
     */
    private HistoricActivityInstance getCurrentApplyActivity(String excutionId, String taskId) {
        HistoricActivityInstance myActivity = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().executionId
                (excutionId).finished().list();
        for (HistoricActivityInstance hai : haiList) {
            if (taskId.equals(hai.getTaskId())) {
                myActivity = hai;
                break;
            }
        }
        return myActivity;
    }

    /**
     * 得到申请流程用户的任务
     *
     * @param processInstanceId 流程实例id
     * @param backNum           回退节点数
     * @return
     */
    private HistoricTaskInstance getApplyUserTask(String processInstanceId, int backNum) {
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByTaskCreateTime().asc().list();
        // 如果返回数有误，直接返回空
        if (backNum <= 0 || taskInstanceList.size() < backNum) {
            return null;
        }
        return taskInstanceList.get(taskInstanceList.size() - backNum);
    }
}
