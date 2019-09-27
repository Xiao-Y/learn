package com.billow.job.service.impl;

import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<ScheduleJobVo> findByJobStatus(ScheduleJobVo scheduleJobVo) {
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
        ExampleMatcher matcher = getExampleMatcher();
        Example<ScheduleJobPo> example = Example.of(scheduleJobPo, matcher);
        List<ScheduleJobPo> scheduleJobPos = scheduleJobDao.findAll(example);
        return ConvertUtils.convert(scheduleJobPos, ScheduleJobVo.class);
    }

    private ExampleMatcher getExampleMatcher() {
        return ExampleMatcher.matching()
                //全部模糊查询，即%{address}%
                .withMatcher("jobName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("jobGroup", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("methodName", ExampleMatcher.GenericPropertyMatchers.contains());
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
        ScheduleJobPo scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
        Pageable pageable = new PageRequest(scheduleJobVo.getPageNo(), scheduleJobVo.getPageSize());
        ExampleMatcher matcher = getExampleMatcher();
        Example<ScheduleJobPo> example = Example.of(scheduleJobPo, matcher);
        Page<ScheduleJobPo> page = scheduleJobDao.findAll(example, pageable);
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
}
