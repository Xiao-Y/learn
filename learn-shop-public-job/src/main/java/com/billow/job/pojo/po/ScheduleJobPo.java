package com.billow.job.pojo.po;


import com.billow.job.pojo.base.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @date 2017年5月8日 上午10:30:40
 */
@Entity
@Table(name = "sys_schedule_job")
public class ScheduleJobPo extends BasePo implements Serializable {

    private static final long serialVersionUID = 6347036356689325574L;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务.0禁用 1启用 2删除
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    /**
     * 任务是否有状态,任务是否有状态,0-无，1-有
     */
    private String isConcurrent;
    /**
     * spring bean
     */
    private String springId;
    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 任务名称
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月6日 下午10:43:41
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 任务名称
     *
     * @param jobName
     * @author XiaoY
     * @date: 2017年5月6日 下午10:43:45
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 任务分组
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月6日 下午10:43:54
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 任务分组
     *
     * @param jobGroup
     * @author XiaoY
     * @date: 2017年5月6日 下午10:43:58
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 任务状态 0禁用 1启用 2删除
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月6日 下午10:44:10
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * 任务状态 0禁用 1启用 2删除
     *
     * @param jobStatus
     * @author XiaoY
     * @date: 2017年5月6日 下午10:44:14
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 任务运行时间表达式
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月6日 下午10:44:29
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 任务运行时间表达式
     *
     * @param cronExpression
     * @author XiaoY
     * @date: 2017年5月6日 下午10:44:33
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 任务描述
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月6日 下午10:56:01
     */
    public String getDescription() {
        return description;
    }

    /**
     * 任务描述
     *
     * @param description
     * @author XiaoY
     * @date: 2017年5月6日 下午10:56:05
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年5月7日 下午4:57:41
     */
    public String getBeanClass() {
        return beanClass;
    }

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param beanClass
     * @date 2017年5月7日 下午4:57:44
     */
    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 任务是否有状态,任务是否有状态,0-无，1-有
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年5月7日 下午4:57:20
     */
    public String getIsConcurrent() {
        return isConcurrent;
    }

    /**
     * 任务是否有状态,任务是否有状态,0-无，1-有
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param isConcurrent
     * @date 2017年5月7日 下午4:57:23
     */
    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    /**
     * spring bean
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年5月7日 下午4:56:58
     */
    public String getSpringId() {
        return springId;
    }

    /**
     * spring bean
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param springId
     * @date 2017年5月7日 下午4:56:53
     */
    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
