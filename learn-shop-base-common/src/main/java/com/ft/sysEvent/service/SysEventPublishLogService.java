package com.ft.sysEvent.service;

import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.model.expand.SysEventPublishLogDto;

import java.util.List;

/**
 * 事务执行日志
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public interface SysEventPublishLogService {
    void save(SysEventPublishLogDto sysEventPublishLogDto);
}
