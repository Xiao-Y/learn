package com.billow.job.service;


import com.billow.job.pojo.vo.ScheduleJobLogVo;

/**
 * 自动任务信息日志接口<br>
 *
 * @author billow<br>
 * @version 2.0
 * @Mail lyongtao123@126.com<br>
 * @date 2017-12-08 15:46:02
 */
public interface ScheduleJobLogService {

    void insert(ScheduleJobLogVo logDto);
}