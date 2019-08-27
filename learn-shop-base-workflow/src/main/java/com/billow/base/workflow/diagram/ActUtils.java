package com.billow.base.workflow.diagram;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程图生成工具
 *
 * @author liuyongtao
 * @create 2019-08-27 8:31
 */
@Component
public class ActUtils {

    private static Logger logger = LoggerFactory.getLogger(ActUtils.class);

    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 获取原始的流程图
     *
     * @param deploymentId 部署id
     * @return java.io.InputStream
     * @author LiuYongTao
     * @date 2019/8/27 12:22
     */
    public InputStream genOriginalProcessImage(String deploymentId) {
        InputStream imageStream = null;
        if (deploymentId == null || "".equals(deploymentId)) {
            logger.error("deploymentId is null");
            return imageStream;
        }
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
            if (processDefinition == null) {
                logger.error("deploymentId:{},没有查询到流程定义", deploymentId);
                return imageStream;
            }
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            imageStream = new CustomProcessDiagramGenerator().generatePngDiagram(bpmnModel);
        } catch (Exception e) {
            logger.error("deploymentId：" + deploymentId + "生成流程图失败，原因：" + e.getMessage(), e);
        }
        return imageStream;
    }

    /**
     * 获取流程图，并且显示活动节点（显示运行轨迹）
     *
     * @param executionId Execution对象ID，任务ID或流程实例ID等正在执行的对象ID
     * @return 流程图输入流
     */
    public InputStream genActivitiProccessImage(String executionId) {
        InputStream imageStream = null;
        if (executionId == null || "".equals(executionId)) {
            logger.error("executionId is null");
            return imageStream;
        }
        try {
            // 查询Execution对象
            Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
            String processInstanceId = execution.getProcessInstanceId();
            // 查询历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            // 查询流程定义对象
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(historicProcessInstance.getProcessDefinitionId())
                    .singleResult();
            // 查询已经执行过的流程节点，包括Gateway等
            List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            // 高亮线路id集合
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            List<String> highLightedFlows = getHighLightedFlows(bpmnModel, highLightedActivitList);
            logger.debug("Executed flow : {}", highLightedFlows);
            // 高亮环节id集合
            List<String> highLightedActivities = new ArrayList<>();
            for (HistoricActivityInstance tempActivity : highLightedActivitList) {
                String activityId = tempActivity.getActivityId();
                highLightedActivities.add(activityId);
            }
            logger.debug("Executed activity: {}", highLightedActivities);
            // 得到正在执行的Activity的Id
            List<String> activityIds = new ArrayList<>();
            getCurrrentActivity(processInstanceId, activityIds);
            logger.debug("Current activity ids : {}", activityIds);
            imageStream = new CustomProcessDiagramGenerator().generateDiagram(bpmnModel, highLightedActivities, highLightedFlows, activityIds);
        } catch (Exception e) {
            logger.error("executionId：" + executionId + "生成流程图失败，原因：" + e.getMessage(), e);
        }
        return imageStream;
    }

    /**
     * 获取已经执行过的流程线，用于高亮显示
     *
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode;
        FlowNode targetFlowNode;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();
            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(sequenceFlow.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }
                    highLightedFlowIds.add(highLightedFlowId);
                }
            }
        }
        return highLightedFlowIds;
    }

    /**
     * 获取流程当前的节点
     *
     * @param processInstanceId
     * @param activityIds
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 8:57
     */
    private void getCurrrentActivity(String processInstanceId, List<String> activityIds) {
        // 查询流程当前活动的执行对象，代办任务节点
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
    }
}
