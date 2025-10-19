package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("v_mytasklist")
@Schema(title = "MytasklistPo对象", description="VIEW")
public class MytasklistPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("executionId")
    private String executionId;

    @TableField("assignee")
    private String assignee;

    @TableField("groupId")
    private String groupId;

    @TableField("taskName")
    private String taskName;

    @TableField("taskId")
    private String taskId;

    @TableField("claimStatus")
    private String claimStatus;

    @TableField("suspensionStatus")
    private Integer suspensionStatus;

    @TableField("procDefId")
    private String procDefId;

    @TableField("procInstId")
    private String procInstId;

    @TableField("isEnd")
    private Boolean isEnd;

    @TableField("applyType")
    private String applyType;

    @TableField("applyUserCode")
    private String applyUserCode;

    @TableField("voClazz")
    private String voClazz;

    @TableField("validInd")
    private Boolean validInd;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("creatorCode")
    private String creatorCode;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    @TableField("updaterCode")
    private String updaterCode;


}
