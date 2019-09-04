package com.billow.base.workflow.component;

import com.billow.base.workflow.vo.DeploymentVo;
import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.base.workflow.vo.TaskVo;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

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
     * @param deploymentVo
     * @return com.billow.base.workflow.vo.Page<com.billow.base.workflow.vo.DeploymentVo>
     * @author LiuYongTao
     * @date 2019/8/29 16:37
     */
    List<DeploymentVo> queryDeployment(DeploymentVo deploymentVo);

    /**
     * 条件查询流程部署
     *
     * @param deploymentVo
     * @param offset
     * @param pageSize
     * @return com.billow.base.workflow.vo.Page<com.billow.base.workflow.vo.DeploymentVo>
     * @author LiuYongTao
     * @date 2019/8/29 16:37
     */
    Page<DeploymentVo> queryDeployment(DeploymentVo deploymentVo, Integer offset, Integer pageSize);

    /**
     * 查询流程定义
     *
     * @param processDefinitionVo
     * @return java.util.List<com.billow.base.workflow.vo.ProcessDefinitionVo>
     * @author LiuYongTao
     * @date 2019/8/29 16:47
     */
    List<ProcessDefinitionVo> queryProcessDefinition(ProcessDefinitionVo processDefinitionVo);

    /**
     * 查询流程定义（分页）
     *
     * @param processDefinitionVo
     * @return com.billow.base.workflow.vo.Page<org.activiti.engine.repository.ProcessDefinition>
     * @author LiuYongTao
     * @date 2019/8/27 19:42
     */
    Page<ProcessDefinitionVo> queryProcessDefinition(ProcessDefinitionVo processDefinitionVo, Integer offset, Integer pageSize);

    /**
     * 查看活动的流程图（显示运行轨迹）
     *
     * @param executionId Execution对象ID，任务ID或流程实例ID等正在执行的对象ID
     * @param response
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 12:08
     */
    void genActiveProccessImage(String executionId, HttpServletResponse response) throws Exception;

    /**
     * 获取原始的流程图
     *
     * @param deploymentId 部署id
     * @param response
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 12:28
     */
    void genOriginalProcessImage(String deploymentId, HttpServletResponse response) throws Exception;

    /**
     * 查询任务列表
     *
     * @param taskVo
     * @param offset
     * @param pageSize
     * @return com.billow.base.workflow.vo.Page<com.billow.base.workflow.vo.TaskVo>
     * @author LiuYongTao
     * @date 2019/8/30 17:44
     */
    Page<TaskVo> queryTaskList(TaskVo taskVo, Integer offset, Integer pageSize);

    /**
     * 通过流程id 查询任务
     *
     * @param processInstanceId
     * @return
     */
    List<Task> queryTasksByProcessId(String processInstanceId);

    /**
     * 通过流程id 查询任务
     *
     * @param processInstanceId
     * @return
     */
    Task queryTaskByProcessId(String processInstanceId);

    /**
     * 通过任务和变量名称获取变量
     *
     * @param taskId
     * @param varName
     * @return
     */
    Object queryVariables(String taskId, String varName);

    /**
     * 根据查询任务的数量
     *
     * @param taskVo
     * @return long
     * @author billow
     * @date 2019/8/31 16:16
     */
    long queryOwnerTaskCount(TaskVo taskVo);

    /**
     * 根据条件查询指定人的任务数量
     *
     * @param taskVo
     * @return long
     * @author billow
     * @date 2019/8/31 16:16
     */
    long queryAssigneeTaskCount(TaskVo taskVo);

    /**
     * 查询个人发起的流程数量（所有的）
     *
     * @param startedBy
     * @return long
     * @author billow
     * @date 2019/8/31 21:26
     */
    long queryMyStartProdeAllCount(String startedBy);

    /**
     * 查询个人发起的流程（还在运行中）
     *
     * @param startedBy
     * @return long
     * @author billow
     * @date 2019/8/31 21:26
     */
    long queryMyStartProdeActiveCount(String startedBy);
}
