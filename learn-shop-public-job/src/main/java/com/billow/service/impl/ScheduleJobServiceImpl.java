package com.billow.service.impl;

import com.billow.dao.ScheduleJobDao;
import com.billow.model.expand.ScheduleJobDto;
import com.billow.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @create 2018-02-28 9:14
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Override
    public List<ScheduleJobDto> findByJobStatus(ScheduleJobDto scheduleJobDto) {
        return scheduleJobDao.findByJobStatus(scheduleJobDto.getJobStatus());
    }

    @Override
    @Transactional
    public void updateJobStatus(ScheduleJobDto dto) {
        ScheduleJobDto jobDto = scheduleJobDao.findOne(dto.getJobId());
        if (jobDto != null) {
            jobDto.setJobStatus(dto.getJobStatus());
            jobDto.setUpdateTime(new Date());
            scheduleJobDao.save(jobDto);
        }
    }

    @Override
    public ScheduleJobDto selectByPrimaryKey(ScheduleJobDto dto) {
        return scheduleJobDao.findOne(dto.getJobId());
    }

    @Override
    @Transactional
    public void updateByPrimaryKeySelective(ScheduleJobDto dto) {
        scheduleJobDao.save(dto);
    }

    @Override
    @Transactional
    public void deleteByPrimaryKey(ScheduleJobDto dto) {
        scheduleJobDao.delete(dto.getJobId());
    }

    @Override
    @Transactional
    public void insert(ScheduleJobDto dto) {
        scheduleJobDao.save(dto);
    }
}
