package com.ft.sysEvent.service;

import com.ft.sysEvent.model.expand.SysEventProcessDto;

import java.util.List;

/**
 * 待处理的事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public interface SysEventProcessService {

    void update(SysEventProcessDto sysEventProcessDto);

    void save(SysEventProcessDto sysEventProcessDto);

    void deleteById(String id);

    List<SysEventProcessDto> findByStatus(String status);

}
