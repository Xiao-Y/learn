package com.billow.task.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * 任务组表（对应 sys_task_group）
 */
@Data
@Table("sys_task_group")
public class SysTaskGroup {
    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务组名称
     */
    private String groupName;

    /**
     * 任务组编号（执行链路跟踪）
     */
    private String groupNo;

    /**
     * 子任务数
     */
    private Integer taskSize;

    /**
     * 子任务执行成功条数
     */
    private Integer successSize;

    /**
     * 任务组执行状态：S：执行成功，W：未执行，I：执行中，F：执行失败
     */
    private String executeStatus;

    /**
     * 任务组状态：S：成功，W：未执行，F：失败
     */
    private String status;

    /**
     * 任务开始执行时间
     */
    private Date executStartTime;

    /**
     * 任务执行结束时间
     */
    private Date executEndTime;

    /**
     * 失败错误信息
     */
    private String msg;

    /**
     * 子任务执行结束条数(成功、失败)
     */
    private Integer executeEndSize;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建人userId
     */
    private Integer createUserId;

    /**
     * 任务创建人的用户类型：HQ：总部端用户，MERCHANT：商家端用户，SYSTEM：门店端用户
     */
    private String createUserType;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 删除状态(2删除,0未删除)
     */
    private String delFlag;

    /**
     * 任务类型（batch：批处理，timed_task：定时任务）
     */
    private String type;

    /**
     * 任务参数
     */
    private String taskParam;

    /**
     * 扩展参数
     */
    private String extendProp;

    /**
     * 用户信息
     */
    private String token;
}