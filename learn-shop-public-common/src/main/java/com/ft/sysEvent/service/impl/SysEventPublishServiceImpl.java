package com.ft.sysEvent.service.impl;

import com.ft.sysEvent.dao.SysEventPublishDao;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.service.SysEventPublishService;
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
}
