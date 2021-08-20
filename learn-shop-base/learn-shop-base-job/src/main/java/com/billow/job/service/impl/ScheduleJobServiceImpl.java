package com.billow.job.service.impl;

import com.billow.job.common.CustomPage;
import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @create 2018-02-28 9:14
 */
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Override
    public List<ScheduleJobVo> findByJobStatus(ScheduleJobVo scheduleJobVo) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
        List<ScheduleJobPo> scheduleJobPos = scheduleJobDao.findAll(scheduleJobPo);
        return ConvertUtils.convert(scheduleJobPos, ScheduleJobVo.class);
    }

    @Override
    public ScheduleJobVo findById(String id) {
        ScheduleJobPo scheduleJobPo = scheduleJobDao.findById(id);
        return ConvertUtils.convert(scheduleJobPo, ScheduleJobVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateById(ScheduleJobVo dto) {
        dto.setUpdateTime(new Date());
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(dto, ScheduleJobPo.class);
        scheduleJobDao.updateById(scheduleJobPo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(String id) {
        scheduleJobDao.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(ScheduleJobVo dto) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(dto, ScheduleJobPo.class);
        scheduleJobPo.setCreateTime(new Date());
        scheduleJobPo.setUpdateTime(new Date());
        scheduleJobPo.setUpdaterCode(dto.getCreatorCode());
        ScheduleJobPo save = scheduleJobDao.save(scheduleJobPo);
        ConvertUtils.convert(save, dto);
    }

    @Override
    public CustomPage<ScheduleJobPo> findAll(ScheduleJobVo scheduleJobVo) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
        return scheduleJobDao.findByPage(scheduleJobPo, scheduleJobVo.getPageNo(), scheduleJobVo.getPageSize());
    }

    @Override
    public ScheduleJobVo findByIdAndValidIndIsTrueAndIsStopIsTrue(String id) {
        ScheduleJobPo po = null;
        ScheduleJobPo scheduleJobPo = new ScheduleJobPo();
        scheduleJobPo.setId(id);
        scheduleJobPo.setValidInd(true);
        scheduleJobPo.setIsExceptionStop(true);
        List<ScheduleJobPo> scheduleJobPos = scheduleJobDao.findAll(scheduleJobPo);
        if (scheduleJobPos != null && scheduleJobPos.size() > 0) {
            po = scheduleJobPos.get(0);
        }
        return ConvertUtils.convert(po, ScheduleJobVo.class);
    }

    @Override
    public long countByJobNameAndJobGroup(String jobName, String jobGroup) {
        return scheduleJobDao.countByJobNameAndJobGroup(jobName, jobGroup);
    }
}
