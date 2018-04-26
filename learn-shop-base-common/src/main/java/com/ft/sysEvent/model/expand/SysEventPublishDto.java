package com.ft.sysEvent.model.expand;

import com.ft.sysEvent.model.domain.SysEventPublishBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 夸系统事务运行操作,记录待发布事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:22
 */
@Entity
@Table(name = "sys_event_publish")
public class SysEventPublishDto extends SysEventPublishBase implements Serializable {
}
