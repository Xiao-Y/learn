package com.billow.job.dao;

import com.billow.job.common.CustomPage;
import com.billow.job.pojo.po.ScheduleJobPo;

import java.util.List;

public interface ScheduleJobDao {

    /**
     * 按照jobName，jobGroup统计已经添加的自动任务的数据量
     *
     * @param jobName
     * @param jobGroup
     * @return int
     * @author LiuYongTao
     * @date 2019/8/15 11:29
     */
    long countByJobNameAndJobGroup(String jobName, String jobGroup);

    /**
     * 根据条件查询自动任务
     *
     * @param scheduleJobPo 查询条件(Id,ValidInd,ExceptionStop必须要，其它根据条件)
     * @return java.util.List<com.billow.job.pojo.po.ScheduleJobPo>
     * @author LiuYongTao
     * @date 2019/12/20 16:39
     */
    List<ScheduleJobPo> findAll(ScheduleJobPo scheduleJobPo);

    /**
     * 通过主键查询自动任务
     *
     * @param id 主键
     * @return com.billow.job.pojo.po.ScheduleJobPo
     * @author LiuYongTao
     * @date 2019/12/20 16:39
     */
    ScheduleJobPo findById(String id);

    /**
     * 通过主键删除自动任务
     *
     * @param id 主键
     * @return void
     * @author LiuYongTao
     * @date 2019/12/20 16:39
     */
    void deleteById(String id);

    /**
     * 保存自动任务
     *
     * @param scheduleJobPo 查询条件
     * @return com.billow.job.pojo.po.ScheduleJobPo
     * @author LiuYongTao
     * @date 2019/12/20 16:40
     */
    ScheduleJobPo save(ScheduleJobPo scheduleJobPo);

    /**
     * 更新自动任务
     *
     * @param scheduleJobPo 查询条件
     * @return void
     * @author LiuYongTao
     * @date 2019/12/20 16:40
     */
    void updateById(ScheduleJobPo scheduleJobPo);

    /**
     * 分布查询自动任务
     *
     * @param scheduleJobPo 查询条件
     * @param pageNo        第几页，从0开始
     * @param pageSize      页面大小
     * @return com.billow.job.common.CustomPage<com.billow.job.pojo.po.ScheduleJobPo>
     * @author LiuYongTao
     * @date 2019/12/20 16:40
     */
    CustomPage<ScheduleJobPo> findByPage(ScheduleJobPo scheduleJobPo, Integer pageNo, Integer pageSize);
}
