package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_mail_template")
@Schema(title = "MailTemplatePo对象", description="")
public class MailTemplatePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("mail_code")
    private String mailCode;

    @TableField("mail_type")
    private String mailType;

    @TableField("data_sources")
    private String dataSources;

    @TableField("subject")
    private String subject;

    @TableField("to_emails")
    private String toEmails;

    @TableField("mail_temp")
    private String mailTemp;

    @TableField("mail_markdown")
    private String mailMarkdown;

    @TableField("run_sql")
    private String runSql;

    @TableField("single_result")
    private Boolean singleResult;

    @TableField("template_path")
    private String templatePath;

    @TableField("description")
    private String description;

    @TableField("attachment")
    private Boolean attachment;

    @TableField("template_name")
    private String templateName;


}
