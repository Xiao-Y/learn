package com.billow.system.pojo.vo;

import com.billow.system.pojo.po.ApplyInfoPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author liuyongtao
 * @create 2019-09-03 20:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplyInfoVo extends ApplyInfoPo implements Serializable {

    private String assignee;
    private String taskId;
    private String taskName;
    private String status;
    private String isEndStatus;
    // 是否挂起,0-不存在，1-活动，2-挂起
    private int suspensionStatus;
}
