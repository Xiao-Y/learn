package com.billow.common.business.sysEvent.service.impl;

import com.billow.common.business.sysEvent.dao.SysEventPublishLogDao;
import com.billow.common.business.sysEvent.model.expand.SysEventPublishLogDto;
import com.billow.common.business.sysEvent.service.SysEventPublishLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 事务执行日志
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Service
public class SysEventPublishLogServiceImpl implements SysEventPublishLogService {

    @Autowired
    private SysEventPublishLogDao sysEventPublishLogDao;

    @Transactional
    @Override
    public void save(SysEventPublishLogDto sysEventPublishLogDto) {
        sysEventPublishLogDao.save(sysEventPublishLogDto);
    }
}