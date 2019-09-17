package com.billow.email.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.billow.email.pojo.po.MailTemplatePo;

/**
 * 邮件模板
 *
 * @author liuyongtao
 * @create 2019-08-21 9:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplateVo extends MailTemplatePo {
    // 邮件内容
    private String mailContent;
}
