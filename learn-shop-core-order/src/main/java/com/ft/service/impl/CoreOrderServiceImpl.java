package com.ft.service.impl;

import com.ft.enums.SysEventEunm;
import com.ft.generator.UUID;
import com.ft.service.CoreOrderService;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.service.SysEventPublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-03-01 14:56
 */
@Service
public class CoreOrderServiceImpl implements CoreOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventPublishService sysEventPublishService;

    @Transactional
    public void sendOrderCar() {

        SysEventPublishDto sysEventPublishDto = new SysEventPublishDto();
        sysEventPublishDto.setClassName("123123");
        sysEventPublishDto.setCreateDate(new Date());
        sysEventPublishDto.setUpdateDate(new Date());
        sysEventPublishDto.setEventType("66666");
        sysEventPublishDto.setId(UUID.generate());
        sysEventPublishDto.setStatus(SysEventEunm.status_publish.getStatusCode());
        sysEventPublishService.save(sysEventPublishDto);

        System.out.println("业务执行完毕...");
    }
}
