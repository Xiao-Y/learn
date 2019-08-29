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
    void getActivitiProccessImage(String executionId, HttpServletResponse response) throws Exception;

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

    Page<Task> queryTaskList(TaskVo taskVo, int pageNo, int pageSize);
}
