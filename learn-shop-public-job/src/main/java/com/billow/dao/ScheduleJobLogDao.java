package com.billow.dao;

import com.billow.model.expand.ScheduleJobLogDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLogDto, String> {
}
