package com.billow.base.workflow.component.impl;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作流执行操作
 *
 * @author liuyongtao
 * @create 2019-08-25 10:38
 */
@Component
public class WorkFlowExecuteImpl implements WorkFlowExecute {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProcessInstanceVo startProcessInstance(String processType, String pk, String businessKey, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        ProcessInstance processInstance = null;
        if ("id".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceById(pk, businessKey, variables);
        } else if ("key".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceByKey(pk, businessKey, variables);
        }
        ProcessInstanceVo ex = new ProcessInstanceVo();
        ex.setProcessInstanceId(processInstance.getProcessInstanceId());
        return ex;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitProcess(String taskId, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        taskService.complete(taskId, variables);
    }
}
