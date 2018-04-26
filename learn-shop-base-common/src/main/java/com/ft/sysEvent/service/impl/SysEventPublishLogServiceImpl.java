package com.ft.sysEvent.service.impl;

import com.ft.sysEvent.dao.SysEventPublishDao;
import com.ft.sysEvent.dao.SysEventPublishLogDao;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.model.expand.SysEventPublishLogDto;
import com.ft.sysEvent.service.SysEventPublishLogService;
import com.ft.sysEvent.service.SysEventPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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