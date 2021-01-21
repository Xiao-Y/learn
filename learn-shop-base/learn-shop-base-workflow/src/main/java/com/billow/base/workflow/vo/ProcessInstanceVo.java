package com.billow.base.workflow.vo;

/**
 * 流程实例
 *
 * @author liuyongtao
 * @create 2019-08-24 18:45
 */
public class ProcessInstanceVo {
    private String processDefinitionId;
    private String processInstanceId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public ProcessInstanceVo setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public ProcessInstanceVo setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }
}
