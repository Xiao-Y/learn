package com.billow.job.service.impl;

import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.pojo.po.ScheduleJobLogPo;
import com.billow.job.pojo.vo.ScheduleJobLogVo;
import com.billow.job.service.ScheduleJobLogService;
import com.billow.tools.utlis.ConvertUtils;
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
    public void insert(ScheduleJobLogVo logDto) {
        ScheduleJobLogPo scheduleJobLogPo = ConvertUtils.convert(logDto, ScheduleJobLogPo.class);
        scheduleJobLogDao.save(scheduleJobLogPo);
    }
}
