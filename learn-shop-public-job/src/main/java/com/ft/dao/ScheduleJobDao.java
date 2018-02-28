package com.ft.dao;

import com.ft.model.expand.ScheduleJobDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJobDao extends JpaRepository<ScheduleJobDto, Integer> {

    /**
     * 通过自动任务状态查询出可运行的自动任务
     *
     * @param jobStatus
     * @return
     */
    List<ScheduleJobDto> findByJobStatus(String jobStatus);
}
