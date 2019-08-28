package com.billow.base.workflow.component;

import com.billow.base.workflow.vo.ProcessInstanceVo;
import org.activiti.engine.repository.Deployment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Map;

/**
 * 工作流执行操作
 *
 * @author billow
 * @date 2019/8/25 10:37
 */
public interface WorkFlowExecute {

    /**
     * 部署流程
     *
     * @param processName 流程文件名（test2.bpmn20.xml中的test）
     * @return org.activiti.engine.repository.Deployment
     * @author billow
     * @date 2019/8/25 10:44
     */
    Deployment deploy(String processName);

    /**
     * 部署流程
     *
     * @param resourceName 资源名称
     * @param inputStream  资源流
     * @return org.activiti.engine.repository.Deployment
     * @author LiuYongTao
     * @date 2019/8/28 20:46
     */
    Deployment deploy(String resourceName, InputStream inputStream);

    /**
     * 删除流程部署(cascade为false时，如果存在流程实例，则会抛出异常)
     *
     * @param deploymentId 部署id
     * @param cascade      是否删除所有
     * @return void
     * @author billow
     * @date 2019/8/25 13:20
     */
    void deleteDeployment(String deploymentId, boolean cascade);

    /**
     * 启动流程实例，如果使用key将默认使用最新版本
     *
     * @param processType pk 的类型，流程定义表（ACT_RE_PROCDEF）的id或者key
     * @param pk          id或者key的值
     * @param businessKey 业务主键
     * @param variables   输入的参数
     * @return ProcessInstanceVo
     * @author billow
     * @date 2019/8/25 10:46
     */
    ProcessInstanceVo startProcessInstance(String processType, String pk, String businessKey, Map<String, Object> variables);

    /**
     * 提交任务
     *
     * @param taskId    任务id
     * @param variables 输入的参数
     * @return void
     * @author billow
     * @date 2019/8/25 10:55
     */
    void commitProcess(String taskId, Map<String, Object> variables);
}
