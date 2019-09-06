package com.billow.system.pojo.po;

import com.billow.base.workflow.vo.CustomPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 申请信息
 *
 * @author liuyongtao
 * @create 2019-09-02 17:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_apply_info")
@EntityListeners(AuditingEntityListener.class)
public class ApplyInfoPo extends CustomPage<ApplyInfoPo> implements Serializable {

    @ApiModelProperty("主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("创建人")
    @CreatedBy
    private String creatorCode;

    @ApiModelProperty("更新人")
    @LastModifiedBy
    private String updaterCode;

    @ApiModelProperty("创建时间")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;

    @ApiModelProperty("有效标志")
    private Boolean validInd;

    @ApiModelProperty("流程定义ID")
    @Column(length = 36)
    private String procDefId;

    @ApiModelProperty("流程实例定义ID")
    @Column(length = 36)
    private String procInstId;

    @ApiModelProperty("流程是否结束")
    @Column(length = 1)
    private Boolean isEnd;

    @ApiModelProperty("申请数据，JSON 格式")
    @Lob
    @Type(type = "text")
    @Column
    private String applyData;

    @ApiModelProperty("申请类型：")
    @Column(length = 20)
    private String applyType;

    @ApiModelProperty("申请人CODE")
    @Column(length = 20)
    private String applyUserCode;

    @ApiModelProperty("JSON 转换格式，暂时不用")
    private String voClazz;
}
