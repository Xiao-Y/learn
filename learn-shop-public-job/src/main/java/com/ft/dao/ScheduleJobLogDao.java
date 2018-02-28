package com.ft.dao;

import com.ft.model.expand.ScheduleJobLogDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLogDto, String> {
}
