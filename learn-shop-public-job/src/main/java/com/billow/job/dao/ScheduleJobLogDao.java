package com.billow.job.dao;

import com.billow.job.pojo.po.ScheduleJobLogPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLogPo, Long> {
}
