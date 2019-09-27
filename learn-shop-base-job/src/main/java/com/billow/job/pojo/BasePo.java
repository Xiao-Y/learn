package com.billow.job.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class BasePo extends BasePage implements Serializable {

    // 主键id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 创建人
    private String creatorCode;

    // 更新人
    private String updaterCode;

    // 创建时间
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;

    // 有效标志
    private Boolean validInd;
}