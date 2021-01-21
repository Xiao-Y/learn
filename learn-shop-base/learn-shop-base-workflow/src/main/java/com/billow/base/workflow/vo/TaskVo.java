package com.billow.base.workflow.vo;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-08-27 9:52
 */
public class TaskVo extends CustomPage implements Serializable {

    private String id;
    private String owner;
    private int assigneeUpdatedCount; // needed for v5 compatibility
    private String originalAssignee; // needed for v5 compatibility
    private String assignee;

    private String parentTaskId;

    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private Date createTime; // The time when the task has been created
    private Date dueDate;
    private int suspensionState = SuspensionState.ACTIVE.getStateCode();
    private String category;

    private List<IdentityLinkEntity> taskIdentityLinkEntities = new ArrayList<>();

    private String executionId;

    private String processInstanceId;
    private ExecutionEntity processInstance;

    private String processDefinitionId;

    private String taskDefinitionKey;
    private String formKey;

    private boolean forcedUpdate;

    private Date claimTime;

    public String getId() {
        return id;
    }

    public TaskVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public TaskVo setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public int getAssigneeUpdatedCount() {
        return assigneeUpdatedCount;
    }

    public TaskVo setAssigneeUpdatedCount(int assigneeUpdatedCount) {
        this.assigneeUpdatedCount = assigneeUpdatedCount;
        return this;
    }

    public String getOriginalAssignee() {
        return originalAssignee;
    }

    public TaskVo setOriginalAssignee(String originalAssignee) {
        this.originalAssignee = originalAssignee;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public TaskVo setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public TaskVo setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
        return this;
    }

    public String getName() {
        return name;
    }

    public TaskVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public TaskVo setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }

    public TaskVo setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TaskVo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public TaskVo setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public TaskVo setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TaskVo setCategory(String category) {
        this.category = category;
        return this;
    }

    public List<IdentityLinkEntity> getTaskIdentityLinkEntities() {
        return taskIdentityLinkEntities;
    }

    public TaskVo setTaskIdentityLinkEntities(List<IdentityLinkEntity> taskIdentityLinkEntities) {
        this.taskIdentityLinkEntities = taskIdentityLinkEntities;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public TaskVo setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public TaskVo setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public ExecutionEntity getProcessInstance() {
        return processInstance;
    }

    public TaskVo setProcessInstance(ExecutionEntity processInstance) {
        this.processInstance = processInstance;
        return this;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public TaskVo setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public TaskVo setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public String getFormKey() {
        return formKey;
    }

    public TaskVo setFormKey(String formKey) {
        this.formKey = formKey;
        return this;
    }

    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public TaskVo setForcedUpdate(boolean forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
        return this;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public TaskVo setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
        return this;
    }
}
