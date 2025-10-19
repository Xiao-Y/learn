package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.LeaveDao;
import com.billow.system.pojo.search.LeaveSearchParam;
import com.billow.system.pojo.po.LeavePo;
import com.billow.system.service.LeaveService;
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
public class LeaveServiceImpl extends HighLevelServiceImpl<LeaveDao, LeavePo,LeaveSearchParam> implements LeaveService {

}

