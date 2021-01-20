package com.billow.job.dao;

import com.billow.job.common.CustomPage;
import com.billow.job.pojo.po.ScheduleJobLogPo;

public interface ScheduleJobLogDao {

    /**
     * 保存自动任务日志
     *
     * @param scheduleJobLogPo
     * @return com.billow.job.pojo.po.ScheduleJobLogPo
     * @author LiuYongTao
     * @date 2019/12/20 16:42
     */
    ScheduleJobLogPo save(ScheduleJobLogPo scheduleJobLogPo);

    /**
     * 分布查询自动任务日志
     *
     * @param scheduleJobLogPo 查询条件
     * @param pageNo           第几页，从0开始
     * @param pageSize         页面大小
     * @return com.billow.job.common.CustomPage<com.billow.job.pojo.po.ScheduleJobLogPo>
     * @author LiuYongTao
     * @date 2019/12/20 16:43
     */
    CustomPage<ScheduleJobLogPo> findByPage(ScheduleJobLogPo scheduleJobLogPo, Integer pageNo, Integer pageSize);
}
