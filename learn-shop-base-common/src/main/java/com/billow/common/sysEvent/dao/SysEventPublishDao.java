package com.billow.common.sysEvent.dao;

import com.billow.common.sysEvent.model.expand.SysEventPublishDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public interface SysEventPublishDao extends JpaRepository<SysEventPublishDto, String> {


    List<SysEventPublishDto> findByStatus(String status);

    List<SysEventPublishDto> findByStatusAndCountLessThanEqual(String status, int count);
}
