package com.billow.task.service.impl;

import com.billow.taskcenter.entity.SysTaskGroup;
import com.billow.taskcenter.entity.SysTaskGroupDetail;
import com.billow.taskcenter.mapper.PosTaskGroupDetailMapper;
import com.billow.taskcenter.mapper.PosTaskGroupMapper;
import com.billow.taskcenter.service.PosTaskGroupService;
import com.billow.taskcenter.util.IdGeneratorUtil;
import com.billow.taskcenter.util.TaskStatusEnum;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PosTaskGroupServiceImpl extends ServiceImpl<PosTaskGroupMapper, SysTaskGroup>
        implements PosTaskGroupService {

    private final PosTaskGroupMapper baseMapper;
    private final PosTaskGroupDetailMapper taskDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTaskGroup loadTask(SysTaskGroup taskGroup) {
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
        SysTaskGroup taskGroup = this.getById(taskId);
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
                    .eq(SysTaskGroupDetail::getTaskId, taskId)
                    .eq(SysTaskGroupDetail::getExecuteStatus, TaskStatusEnum.F.name())
                    .eq(SysTaskGroupDetail::getDelFlag, "0"));

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
        SysTaskGroup taskGroup = this.getById(taskId);
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
    public Page<SysTaskGroup> queryTaskGroupList(Page<SysTaskGroup> page, String taskType, String status) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq(SysTaskGroup::getDelFlag, "0")
                .eq(SysTaskGroup::getType, taskType)
                .eq(SysTaskGroup::getStatus, status)
                .orderBy(SysTaskGroup::getCreateTime, false);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskSize(Long taskId, Integer taskSize) {
        SysTaskGroup taskGroup = new SysTaskGroup();
        taskGroup.setId(taskId);
        taskGroup.setTaskSize(taskSize);
        taskGroup.setExecutStartTime(new Date());
        taskGroup.setUpdateTime(new Date());
        this.updateById(taskGroup);
    }
}