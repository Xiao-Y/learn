package com.billow.search.pojo.po;

import com.billow.search.common.cons.AnalyzerConstant;
import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;

import java.util.Date;

/**
 * ES数据模型
 * <p>
 * Copyright © 2021 xpc1024 All Rights Reserved
 **/
@Data
@IndexName("document")
public class Document {
    /**
     * es中的唯一id
     */
//    @IndexId(type= IdType.CUSTOMIZE)
    private String id;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String orderNo;

    /**
     * 文档标题
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    private String title;
    /**
     * 文档内容
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    private String content;
    /**
     * 数量
     */
    @IndexField(fieldType = FieldType.INTEGER)
    private Integer count;
    /**
     * 日期
     */
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}