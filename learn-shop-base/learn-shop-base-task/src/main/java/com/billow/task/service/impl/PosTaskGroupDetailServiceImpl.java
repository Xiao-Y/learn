package com.billow.task.service.impl;

import com.billow.task.dao.TaskGroupDetailMapper;
import com.billow.task.entity.TaskGroupDetail;
import com.billow.task.service.PosTaskGroupDetailService;
import com.billow.task.util.TaskStatusEnum;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.billow.task.entity.table.SysTaskGroupDetailTableDef.SYS_TASK_GROUP_DETAIL;

@Service
@RequiredArgsConstructor
public class PosTaskGroupDetailServiceImpl extends ServiceImpl<TaskGroupDetailMapper, TaskGroupDetail>
        implements PosTaskGroupDetailService {

    @Override
    public Page<TaskGroupDetail> queryTaskDetailList(Page<TaskGroupDetail> page, String groupNo) {
        if (StringUtils.isBlank(groupNo)) {
            throw new RuntimeException("任务组编号不能为空");
        }

        QueryWrapper wrapper = QueryWrapper.create()
                .eq(TaskGroupDetail::getGroupNo, groupNo)
                .eq(TaskGroupDetail::getDelFlag, "0")
                .orderBy(TaskGroupDetail::getCreateTime, false);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean retryTaskDetail(String taskDetailId) {
        TaskGroupDetail detail = this.getById(taskDetailId);
        if (detail == null) {
            throw new RuntimeException("子任务不存在：" + taskDetailId);
        }

        detail.setExecuteStatus(TaskStatusEnum.W.name());
        detail.setStatus(TaskStatusEnum.W.name());
        detail.setRetryNum(detail.getRetryNum() + 1);
        detail.setUpdateTime(new Date());
        detail.setMsg(null);

        return this.updateById(detail);
    }

    @Override
    public Map<String, Integer> countFinishedByGroupNo(Long taskId) {

        Map<String, Integer> result = new HashMap<>();

        QueryWrapper wrapper = QueryWrapper.create()
                .select(QueryMethods.count(TaskGroupDetail::getId).as("task_count"),
                        SYS_TASK_GROUP_DETAIL.EXECUTE_STATUS)
                .eq(TaskGroupDetail::getTaskId, taskId)
                .eq(TaskGroupDetail::getDelFlag, "0")
                .in(TaskGroupDetail::getExecuteStatus,
                        Arrays.asList(TaskStatusEnum.F.name(), TaskStatusEnum.S.name()))
                .groupBy(TaskGroupDetail::getExecuteStatus);

        List<Map> tempResult = this.listAs(wrapper, Map.class);
        for (Map map : tempResult) {
            // 提取执行状态（String 类型，确保非空）
            String executeStatus = (String) map.get("execute_status"); // 对应数据库下划线字段，或 "executeStatus"（驼峰）
            // 提取统计数量（转换为 Long 类型，避免类型转换异常）
            Long taskCount = (Long) map.get("task_count");

            // 放入最终 Map（key：执行状态，value：任务数量）
            if (executeStatus != null && taskCount != null) {
                result.put(executeStatus, taskCount.intValue());
            }
        }
        return result;
    }

    @Override
    public long countSuccessByGroupNo(Long taskId) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq(TaskGroupDetail::getTaskId, taskId)
                .eq(TaskGroupDetail::getDelFlag, "0")
                .in(TaskGroupDetail::getExecuteStatus, Arrays.asList(TaskStatusEnum.F.name(), TaskStatusEnum.S.name()));
        return this.count(wrapper);
    }
}