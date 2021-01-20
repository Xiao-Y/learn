package com.billow.email.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件模板
 *
 * @author liuyongtao
 * @create 2019-08-20 20:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplatePo extends BasePage implements Serializable {

    public MailTemplatePo() {
    }

    public MailTemplatePo(String mailCode, String mailType, String dataSources, String descritpion) {
        this.mailCode = mailCode;
        this.mailType = mailType;
        this.dataSources = dataSources;
        this.descritpion = descritpion;
    }

    // 主键id
    private Long id;

    // 邮件标识，唯一
    private String mailCode;

    // 邮件类型，1-普通邮件，2-html邮件,4-FreeMarker 模板邮件,5-Thymeleaf 模板邮件
    private String mailType;

    // 数据来源，1-固定内容，2-SQL查询，3-参数设置,4-混合（2、3都有）
    private String dataSources;

    // 数据来源为2-SQL查询时，sql 不能为空
    private String runSql;

    // 邮件模板
    private String mailTemp;

    // 邮件Markdown模板
    private String mailMarkdown;

    // 邮件模板描述
    private String descritpion;

    // 收件人邮箱，多个邮箱以“;”分隔
    private String toEmails;

    // 邮件主题
    private String subject;

    // 使用 Thymeleaf 或者 Freemarker 时,需要指定模板路径
    private String templateName;

    // 使用 Thymeleaf 或者 Freemarker 时，sql 结果集是否单行，true-单行，false-多行
    private Boolean singleResult;

    // 有效标志
    private Boolean validInd;

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
}
