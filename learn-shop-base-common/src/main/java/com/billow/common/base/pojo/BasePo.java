//package com.billow.common.base.pojo;
//
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.EntityListeners;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @author liuyongtao
// * @create 2018-04-27 12:28
// */
//@Data
//@EqualsAndHashCode(callSuper = true)
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//public class BasePo extends BasePage implements Serializable {
//
//    @ApiModelProperty("主键id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ApiModelProperty("创建人")
//    @CreatedBy
//    private String creatorCode;
//
//    @ApiModelProperty("更新人")
//    @LastModifiedBy
//    private String updaterCode;
//
//    @ApiModelProperty("创建时间")
//    @CreatedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//    private Date createTime;
//
//    @ApiModelProperty("更新时间")
//    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//    private Date updateTime;
//
//    @ApiModelProperty("有效标志")
//    private Boolean validInd;
//}