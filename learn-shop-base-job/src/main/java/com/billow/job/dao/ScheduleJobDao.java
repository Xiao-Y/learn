package com.billow.job.dao;

import com.billow.job.pojo.po.ScheduleJobPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleJobDao extends JpaRepository<ScheduleJobPo, Long>, JpaSpecificationExecutor<ScheduleJobPo> {

    /**
     * 通过id 查询出有效的并且是否异常停止为true的自动任务
     *
     * @param id
     * @return com.billow.job.pojo.po.ScheduleJobPo
     * @author LiuYongTao
     * @date 2019/9/27 17:44
     */
    ScheduleJobPo findByIdAndValidIndIsTrueAndIsExceptionStopIsTrue(Long id);

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
