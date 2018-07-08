package com.billow.dao;

import com.billow.pojo.po.ScheduleJobLogPo;
import com.billow.pojo.vo.ScheduleJobLogVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLogPo, Long> {
}
