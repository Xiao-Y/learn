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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
     * @param processInstanceId 流程实例ID
     * @return 流程图输入流
     */
    public InputStream genActiveProccessImage(String processInstanceId) {
        InputStream imageStream = null;
        if (processInstanceId == null || "".equals(processInstanceId)) {
            logger.error("processInstanceId is null");
            return imageStream;
        }
        try {
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
            List<String> highLightedFlows = this.getHighLightedFlows(bpmnModel, highLightedActivitList);
            logger.debug("Executed flow : {}", highLightedFlows);
            // 高亮环节id集合
            List<String> highLightedActivities = new ArrayList<>();
            for (HistoricActivityInstance tempActivity : highLightedActivitList) {
                String activityId = tempActivity.getActivityId();
                highLightedActivities.add(activityId);
            }
            logger.debug("Executed activity: {}", highLightedActivities);
            // 得到正在执行的Activity的Id
            List<String> activityIds = this.getCurrrentActivity(processInstanceId);
            logger.debug("Current activity ids : {}", activityIds);
            imageStream = new CustomProcessDiagramGenerator().generateDiagram(bpmnModel, highLightedActivities, highLightedFlows, activityIds);
        } catch (Exception e) {
            logger.error("processInstanceId：" + processInstanceId + "生成流程图失败，原因：" + e.getMessage(), e);
        }
        return imageStream;
    }

    /**
     * 获取已经执行过的流程线，用于高亮显示
     *
     * @param bpmnModel
     * @param historicActivityInstances
     * @return java.util.List<java.lang.String>
     * @author billow
     * @date 2019/9/14 11:52
     */
    public List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
        List<String> highFlows = new ArrayList<>(); // 用以保存高亮的线flowId

        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 获得当前活动对应的节点信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());
            // 用以保存后续开始时间相同的节点
            List<FlowNode> sameStartTimeNodes = new ArrayList<>();
            //找到紧跟在后面的一个节点
            FlowNode sameActivityImpl1 = null;
            // 第一个节点
            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);
            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                // 后续第1个节点
                HistoricActivityInstance activityImp2_ = historicActivityInstances.get(k);
                //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                if (activityImpl_.getActivityType().equals("userTask")
                        && activityImp2_.getActivityType().equals("userTask")
                        && df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))) {
                } else {
                    //找到紧跟在后面的一个节点
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());
                    break;
                }

            }
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                // 如果第一个节点和第二个节点开始时间相同保存
                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 获得当前活动对应的节点的所有出去的线信息
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows();
            // 对所有的线进行遍历
            for (SequenceFlow pvmTransition : pvmTransitions) {
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;

    }

    /**
     * 获取流程当前的节点
     *
     * @param processInstanceId
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/8/27 8:57
     */
    private List<String> getCurrrentActivity(String processInstanceId) {
        List<String> activityIds = new ArrayList<>();
        // 查询流程当前活动的执行对象，代办任务节点
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        return activityIds;
    }
}
