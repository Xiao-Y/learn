package com.billow.base.workflow.component;

import com.billow.base.workflow.vo.CommentVo;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.DeploymentVo;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.base.workflow.vo.TaskVo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
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
    CustomPage<DeploymentVo> queryDeployment(DeploymentVo deploymentVo, Integer offset, Integer pageSize);

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
    CustomPage<ProcessDefinitionVo> queryProcessDefinition(ProcessDefinitionVo processDefinitionVo, Integer offset, Integer pageSize);

    /**
     * 查看活动的流程图（显示运行轨迹）
     *
     * @param processInstanceId 流程实例ID
     * @param response
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 12:08
     */
    void genActiveProccessImage(String processInstanceId, HttpServletResponse response) throws Exception;

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
    CustomPage<TaskVo> queryTaskList(TaskVo taskVo, Integer offset, Integer pageSize);

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

    /**
     * 通过 key 查询到最新的一个流程定义
     *
     * @param key
     * @return com.billow.base.workflow.vo.ProcessDefinitionVo
     * @author billow
     * @date 2019/9/7 15:07
     */
    ProcessDefinitionVo queryProcessDefinitionByKey(String key);

    /**
     * 通过流程实例id 查询批注信息
     *
     * @param procInstId 流程实例id
     * @return java.util.List<com.billow.base.workflow.vo.CommentVo>
     * @author billow
     * @date 2019/9/8 11:01
     */
    List<CommentVo> findCommentListByProcInstId(String procInstId);

    /**
     * 是否挂起,0-不存在，1-活动，2-挂起
     *
     * @param procInstId 流程实例id
     * @return int
     * @author billow
     * @date 2019/9/8 17:21
     */
    int querySuspensionStatus(String procInstId);

    /**
     * 获取当前流程的申请人
     *
     * @param processId 流程实例id
     * @return
     */
    String queryCurrentApplyUserId(String processId);

    /**
     * 获取当前任务的历史活动实例
     *
     * @param excutionId 任务执行id
     * @param taskId     任务id
     * @return
     */
    HistoricActivityInstance queryCurrentApplyActivity(String excutionId, String taskId);

    /**
     * 查询上一个task
     *
     * @param procInstId 流程实例id
     * @param backNum    回退节点数
     * @return
     */
    HistoricTaskInstance queryApplyUserTask(String procInstId, int backNum);
}
