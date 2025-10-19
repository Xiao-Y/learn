package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.ScheduleJobLogDao;
import com.billow.system.pojo.search.ScheduleJobLogSearchParam;
import com.billow.system.pojo.po.ScheduleJobLogPo;
import com.billow.system.service.ScheduleJobLogService;
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
public class ScheduleJobLogServiceImpl extends HighLevelServiceImpl<ScheduleJobLogDao, ScheduleJobLogPo,ScheduleJobLogSearchParam> implements ScheduleJobLogService {

}

