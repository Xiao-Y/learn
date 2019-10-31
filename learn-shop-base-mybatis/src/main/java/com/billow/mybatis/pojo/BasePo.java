package com.billow.mybatis.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BasePo extends BasePage implements Serializable {

    // 主键id
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    // 创建人
    private String creatorCode;
    // 创建人
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