package com.ft.service.impl;

import com.ft.dao.ScheduleJobLogDao;
import com.ft.model.expand.ScheduleJobLogDto;
import com.ft.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 自动任务日志
 *
 * @author liuyongtao
 * @create 2018-02-28 9:15
 */
@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    @Override
    @Transactional
    public void insert(ScheduleJobLogDto logDto) {
        scheduleJobLogDao.save(logDto);
    }
}
