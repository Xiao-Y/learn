package com.billow.base.workflow.component.impl;

import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.diagram.ActUtils;
import com.billow.base.workflow.utils.PageUtils;
import com.billow.base.workflow.vo.CommentVo;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.DeploymentVo;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.base.workflow.vo.TaskVo;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作流查询操作
 *
 * @author liuyongtao
 * @create 2019-08-25 10:39
 */
@Component
public class WorkFlowQueryImpl implements WorkFlowQuery {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActUtils actUtils;
    @Autowired
    private FormService formService;

    @Override
    public List<DeploymentVo> queryDeployment(DeploymentVo deploymentVo) {
        DeploymentQuery query = repositoryService.createDeploymentQuery();
        this.genDeploymentCondition(deploymentVo, query);
        return PageUtils.converListToList(query.list(), DeploymentVo.class);
    }

    @Override
    public CustomPage<DeploymentVo> queryDeployment(DeploymentVo deploymentVo, Integer offset, Integer pageSize) {
        DeploymentQuery query = repositoryService.createDeploymentQuery();
        this.genDeploymentCondition(deploymentVo, query);
        List<Deployment> list = query.listPage(offset, pageSize);
        return PageUtils.converListToPage(pageSize, query.count(), list, DeploymentVo.class);
    }

    /**
     * 构建部署的查询条件
     *
     * @param deploymentVo
     * @param query
     * @return void
     * @author LiuYongTao
     * @date 2019/8/29 16:40
     */
    private void genDeploymentCondition(DeploymentVo deploymentVo, DeploymentQuery query) {
        query.orderByDeploymenTime().desc();
        if (deploymentVo != null) {
            String category = deploymentVo.getCategory();
            if (category != null) {
                query.deploymentCategory(category);
            }
            String id = deploymentVo.getId();
            if (id != null) {
                query.deploymentId(category);
            }
            String key = deploymentVo.getKey();
            if (key != null) {
                query.deploymentKeyLike(key);
            }
            String name = deploymentVo.getName();
            if (name != null) {
                query.deploymentNameLike(name);
            }
        }
    }

    @Override
    public List<ProcessDefinitionVo> queryProcessDefinition(ProcessDefinitionVo entity) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        // 构建查询条件
        this.genProcessDefCondition(entity, query);
        return PageUtils.converListToList(query.list(), ProcessDefinitionVo.class);
    }

    @Override
    public CustomPage<ProcessDefinitionVo> queryProcessDefinition(ProcessDefinitionVo entity, Integer offset, Integer pageSize) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        // 构建查询条件
        this.genProcessDefCondition(entity, query);
        List<ProcessDefinition> list = query.listPage(offset, pageSize);

        CustomPage<ProcessDefinitionVo> page = new CustomPage<>(pageSize, query.count());

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessDefinitionVo> processDefinitionVos = list.stream().map(m -> {
            ProcessDefinitionVo vo = new ProcessDefinitionVo();
            BeanUtils.copyProperties(m, vo);
            // 判断是否级联挂起
            long count = processInstanceQuery.processDefinitionId(m.getId()).suspended().count();
            vo.setSuspendedCascade(count > 0);
            return vo;
        }).collect(Collectors.toList());
        page.setTableData(processDefinitionVos);

        return page;
    }

    /**
     * 构建 queryProcessDefinition 的查询条件
     *
     * @param entity
     * @param query
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 19:44
     */
    private void genProcessDefCondition(ProcessDefinitionVo entity, ProcessDefinitionQuery query) {
        query.orderByProcessDefinitionVersion().desc();

        String deploymentId = entity.getDeploymentId();
        if (deploymentId != null) {
            query.deploymentId(deploymentId);
        }
        String key = entity.getKey();
        if (key != null) {
            query.processDefinitionKey(key);
        }
        String id = entity.getId();
        if (id != null) {
            query.processDefinitionId(id);
        }
        String name = entity.getName();
        if (name != null) {
            query.processDefinitionNameLike("%" + name + "%");
        }
    }

    @Override
    public void genActiveProccessImage(String processInstanceId, HttpServletResponse response) throws Exception {
        logger.info("[开始]-获取流程图图像");
        InputStream in = actUtils.genActiveProccessImage(processInstanceId);
        if (in == null) {
            return;
        }
        OutputStream out = response.getOutputStream();
        // 把图片的输入流程写入resp的输出流中
        byte[] b = new byte[1024];
        for (int len; (len = in.read(b)) != -1; ) {
            out.write(b, 0, len);
        }
        // 关闭流
        out.close();
        in.close();
        logger.info("[完成]-获取流程图图像");
    }

    @Override
    public void genOriginalProcessImage(String deploymentId, HttpServletResponse response) throws Exception {
        logger.info("[开始]-获取流程图图像");
        InputStream in = actUtils.genOriginalProcessImage(deploymentId);
        if (in == null) {
            return;
        }
        OutputStream out = response.getOutputStream();
        // 把图片的输入流程写入resp的输出流中
        byte[] b = new byte[1024];
        for (int len; (len = in.read(b)) != -1; ) {
            out.write(b, 0, len);
        }
        // 关闭流
        out.close();
        in.close();
        logger.info("[完成]-获取流程图图像");
    }

    @Override
    public CustomPage<TaskVo> queryTaskList(TaskVo taskVo, Integer offset, Integer pageSize) {
        TaskQuery query = taskService.createTaskQuery();
        this.genTaskCondition(query, taskVo);
        List<Task> list = query.listPage(offset, pageSize);
        return PageUtils.converListToPage(pageSize, query.count(), list, TaskVo.class);
    }

    /**
     * 构建任务查询条件
     *
     * @param query
     * @param taskVo
     * @return void
     * @author LiuYongTao
     * @date 2019/8/30 17:46
     */
    private void genTaskCondition(TaskQuery query, TaskVo taskVo) {

        query.orderByTaskCreateTime().desc();

        String owner = taskVo.getOwner();
        if (owner != null) {
            query.taskOwner(owner);
        }

        String id = taskVo.getId();
        if (id != null) {
            query.taskId(id);
        }

        String assignee = taskVo.getAssignee();
        if (assignee != null) {
            query.taskAssignee(assignee);
        }

    }

    @Override
    public Task queryTaskByProcessId(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return task;
    }

    @Override
    public List<Task> queryTasksByProcessId(String processInstanceId) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return tasks;
    }

    @Override
    public Object queryVariables(String taskId, String varName) {
        HistoricVariableInstance variableInstance =
                historyService.createHistoricVariableInstanceQuery().taskId(taskId).variableName(varName).singleResult();
        return variableInstance.getValue();
    }

    @Override
    public long queryOwnerTaskCount(TaskVo taskVo) {
        TaskQuery query = taskService.createTaskQuery();
        this.genTaskCondition(query, taskVo);
        return query.count();
    }

    @Override
    public long queryAssigneeTaskCount(TaskVo taskVo) {
        TaskQuery query = taskService.createTaskQuery();
        this.genTaskCondition(query, taskVo);
        return query.count();
    }

    @Override
    public long queryMyStartProdeAllCount(String startedBy) {
        return historyService.createHistoricProcessInstanceQuery()
                .startedBy(startedBy)
                .count();
    }

    @Override
    public long queryMyStartProdeActiveCount(String startedBy) {
        return runtimeService.createExecutionQuery()
                .startedBy(startedBy)
                .count();
    }

    @Override
    public ProcessDefinitionVo queryProcessDefinitionByKey(String key) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key).
                        latestVersion().
                        singleResult();


        ProcessDefinitionVo vo = new ProcessDefinitionVo();
        if(processDefinition != null){
            BeanUtils.copyProperties(processDefinition, vo);
            Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId());
            System.out.println(renderedStartForm);
        }
        return vo;
    }

    @Override
    public List<CommentVo> findCommentListByProcInstId(String procInstId) {
        List<Comment> comments = taskService.getProcessInstanceComments(procInstId);
        List<CommentVo> commentVos = comments.stream().map(m -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(m, vo);
            return vo;
        }).collect(Collectors.toList());
        return commentVos;
    }

    @Override
    public int querySuspensionStatus(String procInstId) {
        List<Execution> execution = runtimeService.createExecutionQuery().processInstanceId(procInstId).list();
        if (execution == null || execution.size() == 0) {
            return 0;
        }
        for (Execution e : execution) {
            if (e.isSuspended()) {
                return 2;
            }
        }
        return 1;
    }

    @Override
    public String queryCurrentApplyUserId(String processId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId)
                .singleResult();
        String applyUserId = processInstance.getStartUserId();
        return applyUserId;
    }

    @Override
    public HistoricActivityInstance queryCurrentApplyActivity(String excutionId, String taskId) {
        HistoricActivityInstance myActivity = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().executionId
                (excutionId).finished().list();
        for (HistoricActivityInstance hai : haiList) {
            if (taskId.equals(hai.getTaskId())) {
                myActivity = hai;
                break;
            }
        }
        return myActivity;
    }

    @Override
    public HistoricTaskInstance queryApplyUserTask(String processInstanceId, int backNum) {
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByTaskCreateTime().asc().list();
        // 如果返回数有误，直接返回空
        if (backNum <= 0 || taskInstanceList.size() < backNum) {
            return null;
        }
        return taskInstanceList.get(taskInstanceList.size() - backNum);
    }
}
