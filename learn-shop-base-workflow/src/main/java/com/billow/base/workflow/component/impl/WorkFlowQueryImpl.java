package com.billow.base.workflow.component.impl;

import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工作流查询操作
 *
 * @author liuyongtao
 * @create 2019-08-25 10:39
 */
@Component
public class WorkFlowQueryImpl implements WorkFlowQuery {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public Page<Deployment> queryDeployment(DeploymentEntity deploymentEntity, int pageNo, int pageSize) {
        DeploymentQuery query = repositoryService.createDeploymentQuery();

        if (deploymentEntity != null) {
            String category = deploymentEntity.getCategory();
            if (category != null) {
                query.deploymentCategory(category);
            }
            String id = deploymentEntity.getId();
            if (id != null) {
                query.deploymentId(category);
            }
            String key = deploymentEntity.getKey();
            if (key != null) {
                query.deploymentKeyLike(key);
            }
            String name = deploymentEntity.getName();
            if (name != null) {
                query.deploymentNameLike(name);
            }
        }

        List<Deployment> list = query.listPage(pageNo, pageSize);
        long count = query.count();

        Page<Deployment> page = new Page<>(pageSize, count, list);

        return page;
    }

    @Override
    public List<ProcessDefinition> queryProcessDefinition(ProcessDefinitionEntity entity) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.orderByProcessDefinitionVersion().desc();
        String deploymentId = entity.getDeploymentId();
        if (deploymentId != null) {
            query.deploymentId(deploymentId);
        }
        String key = entity.getKey();
        if (key != null) {
            query.processDefinitionKey(key);
        }
        return query.list();
    }
}
