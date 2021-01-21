package com.billow.job.service;


import com.billow.job.common.CustomPage;
import com.billow.job.pojo.po.ScheduleJobLogPo;
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

    /**
     * 插入执行日志
     *
     * @param logDto
     * @return void
     * @author LiuYongTao
     * @date 2019/9/26 16:27
     */
    void insert(ScheduleJobLogVo logDto);


    /**
     * 查询执行日志
     *
     * @param scheduleJobLogVo
     * @return com.billow.job.common.CustomPage<com.billow.job.pojo.po.ScheduleJobLogPo>
     * @author LiuYongTao
     * @date 2019/12/20 18:12
     */
    CustomPage<ScheduleJobLogPo> findAutoTaskLog(ScheduleJobLogVo scheduleJobLogVo);
}