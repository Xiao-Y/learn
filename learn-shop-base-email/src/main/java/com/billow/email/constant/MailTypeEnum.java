package com.billow.email.constant;

import java.util.Objects;

/**
 * 邮件类型
 *
 * @author LiuYongTao
 * @date 2019/9/29 9:00
 */
public enum MailTypeEnum {

    COMMON(MailCst.SYS_FC_DATA_MAIL_COMMON, "html"),// 普通类型邮件也使用html执行器解析
    HTML(MailCst.SYS_FC_DATA_MAIL_HTML, "html"),// 2-html邮件
    FM(MailCst.SYS_FC_DATA_MAIL_FM, "fm"),// FreeMarker 模板邮件
    THF(MailCst.SYS_FC_DATA_MAIL_THF, "thf")// Thymeleaf 模板邮件
    ;

    private String type;
    private String prefix;

    MailTypeEnum(String type, String prefix) {
        this.type = type;
        this.prefix = prefix;
    }

    /**
     * 通过 type 获取实现类的前缀（prefix）
     *
     * @param type
     * @return
     */
    public static String getMailTypeEnum(String type) {
        MailTypeEnum[] values = MailTypeEnum.values();
        for (MailTypeEnum value : values) {
            if (Objects.equals(value.type, type)) {
                return value.prefix;
            }
        }
        return null;
    }
}
