package com.ft.model.expand;


import com.ft.model.domain.ScheduleJobLogBase;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 自动任务信息日志model模型<br>
 *
 * @author billow<br>
 * @version 2.0
 * @Mail lyongtao123@126.com<br>
 * @date 2017-12-08 15:46:02
 */
@Entity
@Table(name = "sys_schedule_job_log")
public class ScheduleJobLogDto extends ScheduleJobLogBase {

    public ScheduleJobLogDto() {
        super();
    }

    /**
     * 主键构造器
     *
     * @param id
     */
    public ScheduleJobLogDto(String id) {
        super(id);
    }

    /**
     * 主键toString 非主键不允许添加
     */
    @Override
    public String toString() {
        return super.toString();
    }
}  