package com.billow.base.workflow.component;

import com.billow.base.workflow.vo.Page;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作流查询操作
 *
 * @author billow
 * @date 2019/8/25 10:38
 */
public interface WorkFlowQuery {

    /**
     * 条件查询流程部署
     *
     * @param deploymentEntity
     * @return com.billow.base.workflow.vo.Page<org.activiti.engine.repository.Deployment>
     * @author billow
     * @date 2019/8/25 12:31
     */
    Page<Deployment> queryDeployment(DeploymentEntity deploymentEntity, int pageNo, int pageSize);

    /**
     * 查询流程定义
     *
     * @param processDefinitionEntity
     * @return java.util.List<org.activiti.engine.repository.ProcessDefinition>
     * @author LiuYongTao
     * @date 2019/8/26 12:23
     */
    List<ProcessDefinition> queryProcessDefinition(ProcessDefinitionEntity processDefinitionEntity);

    void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response) throws Exception;
}
