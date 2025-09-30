//package com.billow.common.base.pojo;
//
//import io.swagger.v3.oas.annotations.media.Schema;
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
//    @Schema(title = "主键id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Schema(title = "创建人")
//    @CreatedBy
//    private String creatorCode;
//
//    @Schema(title = "更新人")
//    @LastModifiedBy
//    private String updaterCode;
//
//    @Schema(title = "创建时间")
//    @CreatedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//    private Date createTime;
//
//    @Schema(title = "更新时间")
//    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//    private Date updateTime;
//
//    @Schema(title = "有效标志")
//    private Boolean validInd;
//}