package com.billow.base.workflow.vo;

import java.io.Serializable;

/**
 * 流程定义
 *
 * @author liuyongtao
 * @create 2019-08-27 19:34
 */
public class ProcessDefinitionVo extends CustomPage implements Serializable {

    private String id;
    private String name;
    private String description;
    private String key;
    private int version;
    private String category;
    private String deploymentId;
    private String resourceName;
    private String diagramResourceName;
    private String engineVersion;
    private boolean suspended;
    private boolean suspendedCascade;

    public String getId() {
        return id;
    }

    public ProcessDefinitionVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProcessDefinitionVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProcessDefinitionVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ProcessDefinitionVo setKey(String key) {
        this.key = key;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public ProcessDefinitionVo setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProcessDefinitionVo setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public ProcessDefinitionVo setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
        return this;
    }

    public String getResourceName() {
        return resourceName;
    }

    public ProcessDefinitionVo setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public ProcessDefinitionVo setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
        return this;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public ProcessDefinitionVo setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
        return this;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public ProcessDefinitionVo setSuspended(boolean suspended) {
        this.suspended = suspended;
        return this;
    }

    public boolean isSuspendedCascade() {
        return suspendedCascade;
    }

    public ProcessDefinitionVo setSuspendedCascade(boolean suspendedCascade) {
        this.suspendedCascade = suspendedCascade;
        return this;
    }
}
