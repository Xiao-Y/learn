package com.billow.dao;

import com.billow.dao.specification.ScheduleJobSpec;
import com.billow.pojo.po.ScheduleJobPo;
import com.billow.pojo.vo.ScheduleJobVo;
import org.springframework.data.domain.Pageable;
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
}
