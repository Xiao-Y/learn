package com.billow.email.constant;

/**
 * 静态常量
 *
 * @author liuyongtao
 * @create 2019-09-17 12:16
 */
public class MailCst {

    /**
     * 模块： adminSystem-dataSourcesType邮件模板类型，1-固定邮件
     */
    public static final String SYS_FC_DATA_SS_FIXED = "1";
    /**
     * 模块： adminSystem-dataSourcesType邮件模板类型，2-SQL查询，3-参数设置
     */
    public static final String SYS_FC_DATA_SS_SQL = "2";
    /**
     * 模块： adminSystem-dataSourcesType邮件模板类型，3-参数设置
     */
    public static final String SYS_FC_DATA_SS_PRO = "3";
    /**
     * 模块： adminSystem-dataSourcesType邮件模板类型，4-混合（2、3都有）
     */
    public static final String SYS_FC_DATA_SS_MIX = "4";

    /**
     * 模块： adminSystem-mailType邮件类型
     */
    public static final String SYS_FC_DATA_MAIL_TYPE = "mailType";
    /**
     * 模块： adminSystem-mailTypeType邮件模板类型，1-普通邮件
     */
    public static final String SYS_FC_DATA_MAIL_COMMON = "1";
    /**
     * 模块： adminSystem-mailTypeType邮件模板类型，2-html邮件
     */
    public static final String SYS_FC_DATA_MAIL_HTML = "2";
    /**
     * 模块： adminSystem-mailTypeType邮件模板类型，4-FreeMarker 模板邮件
     */
    public static final String SYS_FC_DATA_MAIL_FM = "4";
    /**
     * 模块： adminSystem-mailTypeType邮件模板类型，5-Thymeleaf 模板邮件
     */
    public static final String SYS_FC_DATA_MAIL_THF = "5";
    /**
     * SQL点位符
     */
    public static final String SQL_PLACEHOLDER = "#";
    /**
     * 参数点位符
     */
    public static final String PRO_PLACEHOLDER = "$";
}
