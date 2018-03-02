package com.ft.sysEvent.service.impl;

import com.ft.sysEvent.dao.SysEventProcessDao;
import com.ft.sysEvent.model.expand.SysEventProcessDto;
import com.ft.sysEvent.service.SysEventProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 待处理的事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Service
public class SysEventProcessServiceImpl implements SysEventProcessService {

    @Autowired
    private SysEventProcessDao sysEventProcessDao;


    @Transactional
    @Override
    public void update(SysEventProcessDto sysEventProcessDto) {
        sysEventProcessDao.save(sysEventProcessDto);
    }

    @Transactional
    @Override
    public void save(SysEventProcessDto sysEventProcessDto) {
        sysEventProcessDao.save(sysEventProcessDto);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        sysEventProcessDao.delete(id);
    }

    @Override
    public List<SysEventProcessDto> findByStatus(String status) {
        return sysEventProcessDao.findByStatus(status);
    }
}
