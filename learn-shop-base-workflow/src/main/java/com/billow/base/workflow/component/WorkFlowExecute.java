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
     * @param operator    操作人
     * @param key         id或者key的值
     * @param businessKey 业务主键
     * @param variables   输入的参数
     * @return ProcessInstanceVo
     * @author billow
     * @date 2019/8/25 10:46
     */
    ProcessInstanceVo startProcessInstance(String operator, String key, String businessKey, Map<String, Object> variables);

    /**
     * 启动流程实例，如果使用key将默认使用最新版本
     *
     * @param operator    操作人
     * @param processType pk 的类型，流程定义表（ACT_RE_PROCDEF）的id或者key
     * @param pk          id或者key的值
     * @param businessKey 业务主键
     * @param variables   输入的参数
     * @return ProcessInstanceVo
     * @author billow
     * @date 2019/8/25 10:46
     */
    ProcessInstanceVo startProcessInstance(String operator, String processType, String pk, String businessKey, Map<String, Object> variables);

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
     * 设置任务所属人，当 taskCode 为空时，会将新任务都分配给 userId ，否则只会分配指定的 taskCode
     *
     * @param procInstId 流程实例id
     * @param userId     用户id
     * @param taskCode   任务code
     * @return void
     * @author LiuYongTao
     * @date 2020/4/27 9:56
     */
    void setAssignee(String procInstId, String userId, String taskCode);

    /**
     * 通过 taskId 设置任务处理人
     *
     * @param taskId
     * @param userId
     * @return void
     * @author LiuYongTao
     * @date 2020/4/27 10:06
     */
    void setAssignee(String taskId, String userId);


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

    /**
     * 保存批注信息
     *
     * @param userCode   批注人
     * @param procInstId 流程实例id
     * @param taskId     任务id
     * @param comment    批注信息
     * @return void
     * @author billow
     * @date 2019/9/8 10:45
     */
    void addComment(String userCode, String procInstId, String taskId, String comment);

    /**
     * 任务跳转
     *
     * @param taskId              当前任务id
     * @param targetFlowElementId 跳转的目标节点id
     * @return void
     * @author LiuYongTao
     * @date 2019/9/17 8:56
     */
    void jump(String taskId, String targetFlowElementId);
}
