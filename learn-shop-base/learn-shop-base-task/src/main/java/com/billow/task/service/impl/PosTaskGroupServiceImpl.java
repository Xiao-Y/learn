package com.billow.task.service.impl;

import com.billow.task.dao.TaskGroupDetailMapper;
import com.billow.task.dao.TaskGroupMapper;
import com.billow.task.entity.TaskGroup;
import com.billow.task.entity.TaskGroupDetail;
import com.billow.task.service.PosTaskGroupService;
import com.billow.task.util.IdGeneratorUtil;
import com.billow.task.util.TaskStatusEnum;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PosTaskGroupServiceImpl extends ServiceImpl<TaskGroupMapper, TaskGroup>
        implements PosTaskGroupService {

    private final TaskGroupMapper baseMapper;
    private final TaskGroupDetailMapper taskDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskGroup loadTask(TaskGroup taskGroup) {
        taskGroup.setGroupNo(IdGeneratorUtil.generateGroupNo());
        taskGroup.setExecuteStatus(TaskStatusEnum.W.name());
        taskGroup.setStatus(TaskStatusEnum.W.name());
        taskGroup.setTaskSize(0);
        taskGroup.setSuccessSize(0);
        taskGroup.setExecuteEndSize(0);
        taskGroup.setCreateTime(new Date());
        taskGroup.setDelFlag("0");

        this.save(taskGroup);
        return taskGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskProgress(Long taskId, Integer executeEndSize, Integer successSize) {
        TaskGroup taskGroup = this.getById(taskId);
        if (taskGroup == null) {
            throw new RuntimeException("任务组不存在：" + taskId);
        }

        taskGroup.setExecuteEndSize(executeEndSize);
        taskGroup.setSuccessSize(successSize);
        taskGroup.setUpdateTime(new Date());

        Integer totalTaskSize = taskGroup.getTaskSize();
        if (totalTaskSize == null || totalTaskSize == 0) {
            return;
        }

        if (executeEndSize < totalTaskSize) {
            taskGroup.setExecuteStatus(TaskStatusEnum.W.name());
        } else {
            long failCount = taskDetailMapper.selectCountByQuery(QueryWrapper.create()
                    .eq(TaskGroupDetail::getTaskId, taskId)
                    .eq(TaskGroupDetail::getExecuteStatus, TaskStatusEnum.F.name())
                    .eq(TaskGroupDetail::getDelFlag, "0"));

            if (failCount > 0) {
                taskGroup.setExecuteStatus(TaskStatusEnum.F.name());
                taskGroup.setStatus(TaskStatusEnum.F.name());
                taskGroup.setMsg("部分子任务执行失败，失败数：" + failCount);
            } else {
                taskGroup.setExecuteStatus(TaskStatusEnum.S.name());
                taskGroup.setStatus(TaskStatusEnum.S.name());
                taskGroup.setMsg("全部子任务执行成功");
                taskGroup.setExecutEndTime(new Date());
            }
        }

        this.updateById(taskGroup);
    }

    @Override
    public Double queryTaskProgress(Long taskId) {
        TaskGroup taskGroup = this.getById(taskId);
        if (taskGroup == null) {
            return 0.0;
        }

        Integer total = taskGroup.getTaskSize();
        Integer finished = taskGroup.getExecuteEndSize();
        if (total == null || total == 0 || finished == null) {
            return 0.0;
        }

        return (double) Math.round((finished.doubleValue() / total) * 10000) / 100;
    }

    @Override
    public Page<TaskGroup> queryTaskGroupList(Page<TaskGroup> page, String taskType, String status) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq(TaskGroup::getDelFlag, "0")
                .eq(TaskGroup::getType, taskType)
                .eq(TaskGroup::getStatus, status)
                .orderBy(TaskGroup::getCreateTime, false);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskSize(Long taskId, Integer taskSize) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setId(taskId);
        taskGroup.setTaskSize(taskSize);
        taskGroup.setExecutStartTime(new Date());
        taskGroup.setUpdateTime(new Date());
        this.updateById(taskGroup);
    }
}