package com.billow.system.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("v_mytasklist")
@Schema(title = "MytasklistPo对象", description="VIEW")
public class MytasklistPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("executionId")
    private String executionId;

    @Column("assignee")
    private String assignee;

    @Column("groupId")
    private String groupId;

    @Column("taskName")
    private String taskName;

    @Column("taskId")
    private String taskId;

    @Column("claimStatus")
    private String claimStatus;

    @Column("suspensionStatus")
    private Integer suspensionStatus;

    @Column("procDefId")
    private String procDefId;

    @Column("procInstId")
    private String procInstId;

    @Column("isEnd")
    private Boolean end;

    @Column("applyType")
    private String applyType;

    @Column("applyUserCode")
    private String applyUserCode;

    @Column("voClazz")
    private String voClazz;

    @Column("validInd")
    private Boolean validInd;

    @Column("createTime")
    private Date createTime;

    @Column("creatorCode")
    private String creatorCode;

    @Column("updateTime")
    private Date updateTime;

    @Column("updaterCode")
    private String updaterCode;


}
