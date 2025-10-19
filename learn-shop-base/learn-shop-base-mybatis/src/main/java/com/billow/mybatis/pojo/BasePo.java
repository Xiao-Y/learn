package com.billow.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;

    // 创建人
    @Column(value = "creator_code")
    private String creatorCode;

    // 创建人
    @Column(value = "updater_code")
    private String updaterCode;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(value = "create_time")
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(value = "update_time")
    private Date updateTime;

    // 有效标志
    @Column(value = "valid_ind")
    private Boolean validInd;
}