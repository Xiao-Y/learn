package com.ft.sysEvent.model.expand;

import com.ft.sysEvent.model.domain.SysEventProcessBase;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 夸系统事务运行操作,记录待处理事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:21
 */
@Entity
@Table(name = "sys_event_process")
public class SysEventProcessDto extends SysEventProcessBase {
}
