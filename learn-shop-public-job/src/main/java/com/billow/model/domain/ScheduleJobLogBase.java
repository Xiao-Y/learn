package com.billow.model.domain;

import com.billow.model.base.BaseModel;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

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
@MappedSuperclass
public class ScheduleJobLogBase extends BaseModel implements Serializable {

    public ScheduleJobLogBase() {
        super();
    }

    /**
     * 主键构造器
     *
     * @param id
     */
    public ScheduleJobLogBase(String id) {
        super();
        this.id = id;
    }

    @Id
    private String id;
    // 任务名称
    private String jobName;
    // 创建时间
    private Date createTime;
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
     * 创建时间
     *
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * @return
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id
     * @author billow<br>
     * @date: 2017-12-08 15:46:02
     */
    public void setId(String id) {
        this.id = id;
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


    /**
     * 主键toString 非主键不允许添加
     */
    @Override
    public String toString() {
        return "PK[id = " + id + "]";
    }
}  