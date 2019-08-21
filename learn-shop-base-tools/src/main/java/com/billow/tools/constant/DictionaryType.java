package com.billow.tools.constant;

/**
 * 数据字典中模块CODE与字段CODE<br/>
 * 由DictionaryType统一管理<br>
 * 模块：MMM_MODEL_CODE_MMM<br>
 * 字段：MMM_FIELD_CODE_FFF<br>
 *
 * @author XiaoY
 * @date: 2015年10月1日 下午10:56:58
 */
public class DictionaryType {

    /************************** adminSystem ********************************/
    /**
     * 模块： adminSystem 数据字典模块
     */
    public static final String SYS_MC_SYSTEM_MODULE = "adminSystem";
    /**
     * 模块： adminSystem-systemModule数据字典
     */
    public static final String SYS_FC_SYSTEM_MODULE = "systemModule";

    /**
     * 模块： adminSystem-dataSourcesType邮件模板类型
     */
    public static final String SYS_FC_DATA_SS_TYPE = "dataSourcesType";

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
     * 模块： adminSystem-mailTypeType邮件模板类型，3-带附件邮件
     */
    public static final String SYS_FC_DATA_MAIL_ATT = "3";

    /************************** publicJob ********************************/
    public static final String JOB_MC_PUBLIC_JOB = "publicJob";
    /**
     * 邮件发送类型： publicJob-sendMailType
     */
    public static final String JOB_FC_SEND_MAIL = "sendMailType";
    /**
     * 邮件发送类型： publicJob-sendMailType-0 不发送
     */
    public static final String JOB_FC_SEND_MAIL_NO_SEND = "0";

    /************************** publicJob ********************************/

}
