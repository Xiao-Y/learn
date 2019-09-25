package com.billow.email.pojo.po;

import com.billow.email.pojo.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 邮件模板
 *
 * @author liuyongtao
 * @create 2019-08-20 20:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_mail_template")
public class MailTemplatePo extends BasePo implements Serializable {

    public MailTemplatePo() {
    }

    public MailTemplatePo(String mailCode, String mailType, String dataSources, String descritpion) {
        this.mailCode = mailCode;
        this.mailType = mailType;
        this.dataSources = dataSources;
        this.descritpion = descritpion;
    }

    // 邮件标识，唯一
    private String mailCode;

    // 邮件类型，1-普通邮件，2-html邮件,4-FreeMarker 模板邮件,5-Thymeleaf 模板邮件
    private String mailType;

    // 数据来源，1-固定内容，2-SQL查询，3-参数设置,4-混合（2、3都有）
    private String dataSources;

    @Lob
    @Type(type = "text")
    // 数据来源为2-SQL查询时，sql 不能为空
    private String runSql;

    @Lob
    @Type(type = "text")
    // 邮件模板
    private String mailTemp;

    @Lob
    @Type(type = "text")
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

    // 是否带附件，true-单，false-不带
    private Boolean attachment;
}
