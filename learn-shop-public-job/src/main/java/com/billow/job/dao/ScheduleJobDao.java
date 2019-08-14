package com.billow.job.dao;

import com.billow.job.pojo.po.ScheduleJobPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ScheduleJobDao extends JpaRepository<ScheduleJobPo, Long>, JpaSpecificationExecutor<ScheduleJobPo> {

    /**
     * 通过自动任务状态查询出可运行的自动任务
     *
     * @param jobStatus
     * @return
     */
    List<ScheduleJobPo> findByJobStatusEquals(String jobStatus);

    ScheduleJobPo findByIdAndValidIndIsTrueAndIsStopIsTrue(Long id);
}
