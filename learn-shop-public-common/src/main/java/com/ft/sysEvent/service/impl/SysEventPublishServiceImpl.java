package com.ft.sysEvent.service.impl;

import com.ft.enums.SysEventEunm;
import com.ft.sysEvent.dao.SysEventPublishDao;
import com.ft.sysEvent.dao.SysEventPublishLogDao;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.model.expand.SysEventPublishLogDto;
import com.ft.sysEvent.service.SysEventPublishService;
import com.ft.utlis.BeanUtils;
import com.ft.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Service
public class SysEventPublishServiceImpl implements SysEventPublishService {

    @Autowired
    private SysEventPublishDao sysEventPublishDao;
    @Autowired
    private SysEventPublishLogDao sysEventPublishLogDao;

    @Override
    public SysEventPublishDto findById(String id) {
        return sysEventPublishDao.findOne(id);
    }

    @Transactional
    @Override
    public void update(SysEventPublishDto sysEventPublishDto) {
        sysEventPublishDao.save(sysEventPublishDto);
    }

    @Transactional
    @Override
    public void save(SysEventPublishDto sysEventPublishDto) {
        sysEventPublishDao.save(sysEventPublishDto);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        sysEventPublishDao.delete(id);
    }

    @Override
    public List<SysEventPublishDto> findByStatus(String status) {
        return sysEventPublishDao.findByStatus(status);
    }

    @Override
    public List<SysEventPublishDto> findByStatusAndCountLessThanEqual(String status, int count) {
        return sysEventPublishDao.findByStatusAndCountLessThanEqual(status, count);
    }

    @Transactional
    @Override
    public void removeSysEventPublishToEventPublishLog() {
        List<SysEventPublishDto> publishDtos = this.findByStatus(SysEventEunm.status_processed.getStatusCode());
        if (ToolsUtils.isNotEmpty(publishDtos)) {
            for (SysEventPublishDto publishDto : publishDtos) {
                SysEventPublishLogDto eventPublishLogDto = new SysEventPublishLogDto();
                org.springframework.beans.BeanUtils.copyProperties(publishDto, eventPublishLogDto);
                sysEventPublishLogDao.save(eventPublishLogDto);
                sysEventPublishDao.delete(publishDto);
            }
        }
    }
}
