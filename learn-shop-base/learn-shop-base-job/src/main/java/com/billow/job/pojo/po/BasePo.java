package com.billow.job.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePo extends BasePage implements Serializable {

    // 主键id
    private Long id;
    // 创建人
    private String creatorCode;
    // 更新人
    private String updaterCode;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date createTime;
    // 更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;
    // 有效标志
    private Boolean validInd;
}