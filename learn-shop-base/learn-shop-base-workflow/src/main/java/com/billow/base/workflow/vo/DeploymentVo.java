package com.billow.base.workflow.vo;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-08-27 19:38
 */
public class DeploymentVo extends CustomPage {

    private String id;
    private String name;
    private String category;
    private String key;
    private Date deploymentTime;
    private String engineVersion;

    public String getId() {
        return id;
    }

    public DeploymentVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeploymentVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public DeploymentVo setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getKey() {
        return key;
    }

    public DeploymentVo setKey(String key) {
        this.key = key;
        return this;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public DeploymentVo setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
        return this;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public DeploymentVo setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
        return this;
    }
}
