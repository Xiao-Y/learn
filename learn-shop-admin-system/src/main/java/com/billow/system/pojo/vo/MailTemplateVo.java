package com.billow.system.pojo.vo;

import com.billow.system.pojo.po.MailTemplatePo;
import lombok.Data;

/**
 * 邮件模板
 *
 * @author liuyongtao
 * @create 2019-08-21 9:23
 */
@Data
public class MailTemplateVo extends MailTemplatePo {
    // 邮件内容
    private String mailContent;
}
