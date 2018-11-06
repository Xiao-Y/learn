package com.billow.job.pojo.po;

import com.billow.job.pojo.base.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 自动任务信息日志数据库模型<br>
 * <p>
 * 对应的表名：sys_schedule_job_log
 *
 * @author billow<br>
 * @version 2.0
 * @Mail lyongtao123@126.com<br>
 * @date 2017-12-08 15:46:02
 */
@Entity
@Table(name = "sys_schedule_job_log")
public class ScheduleJobLogPo extends BasePo implements Serializable {

    // 任务名称
    private String jobName;
    // 任务分组
    private String jobGroup;
    // 自动任务id
    private Integer jobId;
    // 错误信息
    private String info;

    /**
     * 任务名称
     *
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public String getJobName() {
        return this.jobName;
    }

    /**
     * 任务名称
     *
     * @param jobName
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 任务分组
     *
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public String getJobGroup() {
        return this.jobGroup;
    }

    /**
     * 任务分组
     *
     * @param jobGroup
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 自动任务id
     *
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public Integer getJobId() {
        return this.jobId;
    }

    /**
     * 自动任务id
     *
     * @param jobId
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 错误信息
     *
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * 错误信息
     *
     * @param info
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setInfo(String info) {
        this.info = info;
    }

}  