package com.billow.service.impl;

import com.billow.dao.ScheduleJobDao;
import com.billow.dao.specification.ScheduleJobSpec;
import com.billow.pojo.po.ScheduleJobPo;
import com.billow.pojo.vo.ScheduleJobVo;
import com.billow.service.ScheduleJobService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<ScheduleJobVo> findByJobStatus(ScheduleJobVo scheduleJobVo) {
        List<ScheduleJobPo> scheduleJobPos = scheduleJobDao.findByJobStatusEquals(scheduleJobVo.getJobStatus());
        return ConvertUtils.convert(scheduleJobPos, ScheduleJobVo.class);
    }

    @Override
    @Transactional
    public void updateJobStatus(ScheduleJobVo vo) {
        ScheduleJobPo jobDto = scheduleJobDao.findOne(vo.getId());
        if (jobDto != null) {
            jobDto.setJobStatus(vo.getJobStatus());
            jobDto.setUpdateTime(new Date());
            scheduleJobDao.save(jobDto);
        }
    }

    @Override
    public ScheduleJobVo selectByPrimaryKey(ScheduleJobVo dto) {
        ScheduleJobPo scheduleJobPo = scheduleJobDao.findOne(dto.getId());
        return ConvertUtils.convert(scheduleJobPo, ScheduleJobVo.class);
    }

    @Override
    @Transactional
    public void updateByPrimaryKeySelective(ScheduleJobVo dto) {
        scheduleJobDao.save(dto);
    }

    @Override
    @Transactional
    public void deleteByPrimaryKey(ScheduleJobVo dto) {
        scheduleJobDao.delete(dto.getId());
    }

    @Override
    @Transactional
    public void insert(ScheduleJobVo dto) {
        scheduleJobDao.save(dto);
    }

    @Override
    public Page<ScheduleJobPo> selectAll(ScheduleJobVo scheduleJobVo) {
        Pageable pageable = new PageRequest(scheduleJobVo.getPageNo(), scheduleJobVo.getPageSize());
        ScheduleJobSpec scheduleJobSpec = new ScheduleJobSpec(scheduleJobVo);
        Page<ScheduleJobPo> page = scheduleJobDao.findAll(scheduleJobSpec, pageable);
        return page;
    }
}
