package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author billow
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("v_mytasklist")
@ApiModel(value="MytasklistPo对象", description="VIEW")
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
    private Boolean end;

    @TableField("applyType")
    private String applyType;

    @TableField("applyUserCode")
    private String applyUserCode;

    @TableField("voClazz")
    private String voClazz;

    @TableField("validInd")
    private Boolean validInd;

    @TableField("createTime")
    private Date createTime;

    @TableField("creatorCode")
    private String creatorCode;

    @TableField("updateTime")
    private Date updateTime;

    @TableField("updaterCode")
    private String updaterCode;


}
