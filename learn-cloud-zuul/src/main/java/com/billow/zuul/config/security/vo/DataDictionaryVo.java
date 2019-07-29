package com.billow.zuul.config.security.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataDictionaryVo implements Serializable {
    private Long id;
    // 系统模块
    private String systemModule;
    // 下拉字段分类
    private String fieldType;
    // 显示的名称
    private String fieldDisplay;
    // 显示名称的值
    private String fieldValue;
    // 字段排序
    private Integer fieldOrder;
}