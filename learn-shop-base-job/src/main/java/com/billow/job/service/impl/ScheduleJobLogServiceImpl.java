package com.billow.job.service.impl;

import com.billow.job.common.CustomPage;
import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.pojo.po.ScheduleJobLogPo;
import com.billow.job.pojo.vo.ScheduleJobLogVo;
import com.billow.job.service.ScheduleJobLogService;
import com.billow.job.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(ScheduleJobLogVo logDto) {
        ScheduleJobLogPo scheduleJobLogPo = ConvertUtils.convert(logDto, ScheduleJobLogPo.class);
        ScheduleJobLogPo save = scheduleJobLogDao.save(scheduleJobLogPo);
        ConvertUtils.convert(save, logDto);
    }

    @Override
    public CustomPage<ScheduleJobLogPo> findAutoTaskLog(ScheduleJobLogVo scheduleJobLogVo) {
        ScheduleJobLogPo scheduleJobLogPo = ConvertUtils.convert(scheduleJobLogVo, ScheduleJobLogPo.class);
        return scheduleJobLogDao.findByPage(scheduleJobLogPo, scheduleJobLogVo.getPageNo(), scheduleJobLogVo.getPageSize());
    }
}
