package com.billow.job.service.impl;

import com.billow.common.jpa.DefaultSpec;
import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.dao.specification.ScheduleJobSpec;
import com.billow.job.pojo.po.ScheduleJobLogPo;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobLogVo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.ScheduleJobService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    @Override
    public List<ScheduleJobVo> findByJobStatus(ScheduleJobVo scheduleJobVo) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
        DefaultSpec<ScheduleJobPo> defaultSpec = new DefaultSpec<>(scheduleJobPo);
        List<ScheduleJobPo> scheduleJobPos = scheduleJobDao.findAll(defaultSpec);
        return ConvertUtils.convert(scheduleJobPos, ScheduleJobVo.class);
    }

    @Override
    public ScheduleJobVo selectByPK(Long id) {
        ScheduleJobPo scheduleJobPo = scheduleJobDao.findOne(id);
        return ConvertUtils.convert(scheduleJobPo, ScheduleJobVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateByPk(ScheduleJobVo dto) {
        this.save(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByPK(Long id) {
        scheduleJobDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(ScheduleJobVo dto) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(dto, ScheduleJobPo.class);
        ScheduleJobPo save = scheduleJobDao.save(scheduleJobPo);
        ConvertUtils.convert(save, dto);
    }

    @Override
    public Page<ScheduleJobPo> selectAll(ScheduleJobVo scheduleJobVo) {
        Pageable pageable = new PageRequest(scheduleJobVo.getPageNo(), scheduleJobVo.getPageSize());
        ScheduleJobSpec scheduleJobSpec = new ScheduleJobSpec(scheduleJobVo);
        Page<ScheduleJobPo> page = scheduleJobDao.findAll(scheduleJobSpec, pageable);
        return page;
    }

    @Override
    public ScheduleJobVo findByIdAndValidIndIsTrueAndIsStopIsTrue(Long id) {
        ScheduleJobPo po = scheduleJobDao.findByIdAndValidIndIsTrueAndIsExceptionStopIsTrue(id);
        return ConvertUtils.convert(po, ScheduleJobVo.class);
    }

    @Override
    public int countByJobNameAndJobGroup(String jobName, String jobGroup) {
        return scheduleJobDao.countByJobNameAndJobGroup(jobName, jobGroup);
    }

    @Override
    public Page<ScheduleJobLogPo> findAutoTaskLog(ScheduleJobLogVo scheduleJobLogVo) {
        ScheduleJobLogPo scheduleJobLogPo = ConvertUtils.convert(scheduleJobLogVo, ScheduleJobLogPo.class);
        DefaultSpec<ScheduleJobLogPo> defaultSpec = new DefaultSpec<>(scheduleJobLogPo);
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(scheduleJobLogVo.getPageNo(), scheduleJobLogVo.getPageSize(), sort);
        Page<ScheduleJobLogPo> page = scheduleJobLogDao.findAll(defaultSpec, pageable);
        return page;
    }
}
