package com.billow.task.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * 任务明细表（对应 sys_task_group_detail）
 */
@Data
@Table("sys_task_group_detail")
public class SysTaskGroupDetail {
    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务组编码
     */
    private String groupNo;

    /**
     * 请求状态码
     */
    private String code;

    /**
     * 请求接口失败错误信息
     */
    private String msg;

    /**
     * 任务状态：S：执行成功，W：未执行，I：执行中，F：执行失败
     */
    private String executeStatus;

    /**
     * 任务组执行状态：S：成功，W：未执行，F：失败
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
     * 删除标记 1：启用，2：停用(逻辑删除)
     */
    private String recordStatus = "0";

    /**
     * 重试次数
     */
    private Integer retryNum = 0;

    /**
     * 任务参数（长文本）
     */
    private String taskParam;

    /**
     * 关联单号
     */
    private String relationOrderNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

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

}