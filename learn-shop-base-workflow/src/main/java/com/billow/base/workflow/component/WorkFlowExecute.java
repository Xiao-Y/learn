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
     * <p/>
     * cascade为true时，能删除启动的流程，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
     *
     * @param deploymentId 部署id
     * @param cascade      是否删除所有
     * @return void
     * @author billow
     * @date 2019/8/25 13:20
     */
    void deleteDeployment(String deploymentId, boolean cascade);

    /**
     * 挂起流程定义
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void suspendProcess(String processDefinitionId) throws Exception;

    /**
     * 挂起流程定义，挂起所有相关的流程实例
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void suspendProcessCascade(String processDefinitionId) throws Exception;

    /**
     * 激活流程定义
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void activateProcess(String processDefinitionId) throws Exception;

    /**
     * 激活流程定义，激活所有相关的流程实例
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void activateProcessCascade(String processDefinitionId) throws Exception;


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
     * @param taskId 任务id
     * @return void
     * @author billow
     * @date 2019/8/25 10:55
     */
    void commitProcess(String taskId);

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

    /**
     * 设置任务认领组
     *
     * @param taskId
     * @param groupId
     */
    void addCandidateGroup(String taskId, String groupId);


    /**
     * 认领任务
     *
     * @param taskId 任务id
     * @param userId 认领人id
     */
    void claim(String taskId, String userId);

    /**
     * 放弃认领任务
     *
     * @param taskId
     * @return void
     * @author LiuYongTao
     * @date 2019/8/30 18:04
     */
    void unclaim(String taskId);

    /**
     * 设置认证用户，用于定义流程启动人
     *
     * @param userId
     */
    void setAuthUser(String userId);

    /**
     * 任务回退
     *
     * @param taskId  当前任务id
     * @param userId  用户id
     * @param reason  理由
     * @param groupId 分组id
     * @param backNum 退回数
     * @throws Exception
     */
    void rollBackTask(String taskId, String userId, String reason, String groupId, int backNum) throws
            Exception;
}
