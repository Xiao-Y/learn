package com.billow.base.workflow.extend;

import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import java.util.List;

/**
 * 根据提供节点和执行对象id，进行跳转命令
 *
 * @author LiuYongTao
 * @date 2019/9/17 8:54
 */
public class SetFLowNodeAndGoCmd implements Command<Void> {

    private FlowNode flowElement;
    private String executionId;

    public SetFLowNodeAndGoCmd(FlowNode flowElement, String executionId) {
        this.flowElement = flowElement;
        this.executionId = executionId;
    }

    public Void execute(CommandContext commandContext) {
        //获取目标节点的来源连线
        List<SequenceFlow> flows = flowElement.getIncomingFlows();
        if (flows == null || flows.size() < 1) {
            throw new ActivitiException("回退错误，目标节点没有来源连线");
        }
        //随便选一条连线来执行，时当前执行计划为，从连线流转到目标节点，实现跳转
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);
        executionEntity.setCurrentFlowElement(flows.get(0));
        commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
        return null;
    }
}