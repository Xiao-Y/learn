package com.billow.task.process.impl;

import cn.hutool.core.util.StrUtil;
import com.billow.taskcenter.entity.SysTaskGroup;
import com.billow.taskcenter.entity.SysTaskGroupDetail;
import com.billow.taskcenter.process.TaskProcessService;
import com.billow.taskcenter.util.TaskStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BatchTaskProcessServiceImpl implements TaskProcessService {

    @Override
    public List<SysTaskGroupDetail> splitTask(SysTaskGroup sysTaskGroup) {
        List<SysTaskGroupDetail> detailList = new ArrayList<>();

        try {
            String taskParam = sysTaskGroup.getTaskParam();
            if (StrUtil.isEmpty(taskParam)) {
                return detailList;
            }
            List<String> dataIds = StrUtil.split(taskParam, ",");

            for (String dataId : dataIds) {
                SysTaskGroupDetail detail = new SysTaskGroupDetail();
                detail.setTaskId(sysTaskGroup.getId());
                detail.setGroupNo(sysTaskGroup.getGroupNo());
                detail.setExecuteStatus(TaskStatusEnum.W.name());
                detail.setStatus(TaskStatusEnum.W.name());
                detail.setRetryNum(0);
                detail.setCreateTime(new Date());
                detail.setTaskParam(dataId);
                detail.setCreateUser(sysTaskGroup.getCreateUser());

                detailList.add(detail);
            }
        } catch (Exception e) {
            log.error("批处理任务拆分失败，groupNo：{}", sysTaskGroup.getGroupNo(), e);
            throw new RuntimeException("批处理任务拆分异常：" + e.getMessage());
        }

        return detailList;
    }

    @Override
    public SysTaskGroupDetail executeTask(SysTaskGroupDetail detail) {
        try {
            detail.setExecuteStatus(TaskStatusEnum.W.name());
            detail.setExecutStartTime(new Date());
            detail.setUpdateTime(new Date());

            Long dataId = Long.parseLong(detail.getTaskParam());
            if (dataId == null) {
                detail.setExecuteStatus(TaskStatusEnum.F.name());
                detail.setStatus(TaskStatusEnum.F.name());
                detail.setMsg("子任务无数据可处理");
                detail.setExecutEndTime(new Date());
                return detail;
            }

            boolean executeSuccess = this.handleBatchData(dataId);

            if (executeSuccess) {
                detail.setExecuteStatus(TaskStatusEnum.S.name());
                detail.setStatus(TaskStatusEnum.S.name());
                detail.setMsg("执行成功");
            } else {
                detail.setExecuteStatus(TaskStatusEnum.F.name());
                detail.setStatus(TaskStatusEnum.F.name());
                detail.setMsg("数据处理失败");
            }

            detail.setExecutEndTime(new Date());
        } catch (Exception e) {
            log.error("子任务执行失败，taskId：{}", detail.getTaskId(), e);
            detail.setExecuteStatus(TaskStatusEnum.F.name());
            detail.setStatus(TaskStatusEnum.F.name());
            detail.setMsg("执行异常：" + e.getMessage());
            detail.setExecutEndTime(new Date());
        }

        return detail;
    }

    @Override
    public String supportTaskType() {
        return "batch";
    }

    private boolean handleBatchData(Long dataId) {
        log.info("处理批处理数据，分片参数：{}", dataId);
        return true;
    }
}