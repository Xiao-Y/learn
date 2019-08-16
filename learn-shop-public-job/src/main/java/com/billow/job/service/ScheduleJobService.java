package com.billow.job.service;

import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import org.springframework.data.domain.Page;

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

//    /**
//     * 启用、禁用自动任务
//     * <p>
//     * <br>
//     * added by liuyongtao<br>
//     *
//     * @param jobId     自动任务id
//     * @param jobStatus 任务状态，1-启用，0-禁用
//     * @return
//     * @date 2017年5月11日 下午2:58:16
//     */
//    void updateJobStatus(ScheduleJobVo dto);

    ScheduleJobVo selectByPK(Long id);

    void updateByPk(ScheduleJobVo dto);

    void deleteByPK(Long id);

    void save(ScheduleJobVo scheduleJobVo);

    /**
     * 根据条件查询自动任务
     *
     * @param scheduleJobVo
     * @return
     */
    Page<ScheduleJobPo> selectAll(ScheduleJobVo scheduleJobVo);

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
