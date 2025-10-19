package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.ScheduleJobDao;
import com.billow.system.pojo.search.ScheduleJobSearchParam;
import com.billow.system.pojo.po.ScheduleJobPo;
import com.billow.system.service.ScheduleJobService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2025-10-19
 */
@Service
public class ScheduleJobServiceImpl extends HighLevelServiceImpl<ScheduleJobDao, ScheduleJobPo,ScheduleJobSearchParam> implements ScheduleJobService {

}

