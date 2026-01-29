package com.billow.task.service;

import com.billow.taskcenter.entity.SysTaskGroupDetail;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.Map;

public interface PosTaskGroupDetailService extends IService<SysTaskGroupDetail> {

    Page<SysTaskGroupDetail> queryTaskDetailList(Page<SysTaskGroupDetail> page, String groupNo);

    boolean retryTaskDetail(String taskDetailId);

    Map<String, Integer> countFinishedByGroupNo(Long taskId);

    long countSuccessByGroupNo(Long taskId);
}