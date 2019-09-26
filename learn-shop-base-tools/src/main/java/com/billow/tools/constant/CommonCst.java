package com.billow.tools.constant;

/**
 * 公共静态变量
 *
 * @author liuyongtao
 * @create 2017-11-21 17:38
 */
public class CommonCst {

    /**
     * 上传文件的类型-markdown
     *
     * @author LiuYongTao
     * @date 2019/8/8 10:15
     */
    public static final String UPLOAD_TYPE_MARKDOWN = "markdown";
    /**
     * 上传文件的类型-userIcon
     *
     * @author LiuYongTao
     * @date 2019/8/8 10:15
     */
    public static final String UPLOAD_TYPE_USER_ICON = "userIcon";

    /**
     * 审核状态，0-提交
     *
     * @author billow
     * @date 2019/9/8 19:49
     */
    public static final String APPROVE_STATUS_SUBMIT = "0";
    /**
     * 审核状态：1-退回后提交
     *
     * @author billow
     * @date 2019/9/8 19:50
     */
    public static final String APPROVE_STATUS_resubmit = "1";
    /**
     * 审核状态：2-退回
     *
     * @author billow
     * @date 2019/9/8 19:50
     */
    public static final String APPROVE_STATUS_REWORK = "2";

    // 执行类型：1-spring id
    public static final String CLASS_TYPE_SPRING_ID = "1";
    // 执行类型：2-全类名
    public static final String CLASS_TYPE_BEAN_CLASS = "2";
}
