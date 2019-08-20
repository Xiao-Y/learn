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
    public static final String SYS_MODEL_CODE_SYSTEM_MODULE = "adminSystem";
    /**
     * 模块： adminSystem-systemModule数据字典
     */
    public static final String SYS_FIELD_CODE_SYSTEM_MODULE = "systemModule";

    /************************** publicJob ********************************/
    public static final String SYS_MODEL_CODE_PUBLIC_JOB = "publicJob";
    /**
     * 邮件发送类型： publicJob-sendMailType
     */
    public static final String SYS_FIELD_CODE_SEND_MAIL = "sendMailType";
    /**
     * 邮件发送类型： publicJob-sendMailType-0 不发送
     */
    public static final String SYS_FIELD_CODE_SEND_MAIL_NO_SEND = "0";

}
