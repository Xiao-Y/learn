package com.billow.job.service;

import com.billow.job.common.CustomPage;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;

import java.util.List;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @date 2017年5月12日 上午8:43:34
 */
public interface ScheduleJobService {

    /**
     * 查询所有自动任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJobVo
     * @return
     * @date 2017年5月12日 上午8:43:46
     */
    List<ScheduleJobVo> findByJobStatus(ScheduleJobVo scheduleJobVo);


    ScheduleJobVo findById(Long id);

    void updateById(ScheduleJobVo dto);

    void deleteById(Long id);

    void save(ScheduleJobVo scheduleJobVo);

    /**
     * 根据条件查询自动任务
     *
     * @param scheduleJobVo
     * @return
     */
    CustomPage<ScheduleJobPo> findAll(ScheduleJobVo scheduleJobVo);

    /**
     * 通过id 查询出有效的并且是否异常停止为true的自动任务
     *
     * @param id
     * @return com.billow.job.pojo.vo.ScheduleJobVo
     * @author LiuYongTao
     * @date 2019/9/27 17:45
     */
    ScheduleJobVo findByIdAndValidIndIsTrueAndIsStopIsTrue(Long id);

    /**
     * 按照jobName，jobGroup统计已经添加的自动任务的数据量
     *
     * @param jobName
     * @param jobGroup
     * @return int
     * @author LiuYongTao
     * @date 2019/8/15 11:29
     */
    int countByJobNameAndJobGroup(String jobName, String jobGroup);
}
