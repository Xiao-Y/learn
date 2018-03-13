package com.ft.sysEvent.service;

import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 待发布事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public interface SysEventPublishService {

    SysEventPublishDto findById(String id);

    void update(SysEventPublishDto sysEventPublishDto);

    void save(SysEventPublishDto sysEventPublishDto);

    void deleteById(String id);

    List<SysEventPublishDto> findByStatus(String status);

    List<SysEventPublishDto> findByStatusAndCountLessThanEqual(String status, int count);

    void removeSysEventPublishToEventPublishLog();
}
