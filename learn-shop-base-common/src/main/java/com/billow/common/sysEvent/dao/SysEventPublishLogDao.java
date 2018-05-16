package com.billow.common.sysEvent.dao;

import com.billow.common.sysEvent.model.expand.SysEventPublishLogDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 事务执行日志
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public interface SysEventPublishLogDao extends JpaRepository<SysEventPublishLogDto, String> {

}
