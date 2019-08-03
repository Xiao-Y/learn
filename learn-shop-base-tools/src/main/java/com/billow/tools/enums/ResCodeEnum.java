package com.billow.tools.enums;

import java.util.Objects;

/**
 * 状态码
 */
public enum ResCodeEnum {

    /**
     * "0000", "成功"
     */
    RESCODE_SUCCESS("0000", "成功"),
    /**
     * "1111", "成功获取令牌"
     */
    RESCODE_ASSESS_TOKEN("1111", "成功获取令牌"),
    /**
     * "0001", "报文不可解析"
     */
    RESCODE_BAD_REQUEST("0001", "报文不可解析"),
    /**
     * "0002", "报文头错误"
     */
    RESCODE_BAD_REQHEADER("0002", "报文头错误"),
    /**
     * "0003", "报文体格式错误"
     */
    RESCODE_BAD_REQBODY("0003", "报文体格式错误"),
    /**
     * "0004", "当前服务不可用"
     */
    RESCODE_SERVICE_UNREACHABLE("0004", "当前服务不可用"),
    /**
     * "0005", "签名校验错误"
     */
    RESCODE_SIGNATURE_ERROR("0005", "签名校验错误"),
    /**
     * "0006", "权限校验未通过"
     */
    RESCODE_UNAUTHORIZED("0006", "权限校验未通过"),
    /**
     * "0007", "无法查询到相关信息或查询结果为空"
     */
    RESCODE_NULL_RESULT("0007", "无法查询到相关信息或查询结果为空"),
    /**
     * "0008", "网络超时"
     */
    RESCODE_TIMEOUT("0008", "网络超时"),
    /**
     * "0009", "无相关权限"
     */
    RESCODE_FORBIDDEN("0009", "无相关权限"),
    /**
     * "0010", "不符合相关业务规则"
     */
    RESCODE_RULE_UNMATCH("0010", "不符合相关业务规则"),
    /**
     * "0011", "日期格式解析错误"
     */
    RESCODE_FORMAT_DATETIME_UNRESOLVED("0011", "日期格式解析错误"),
    /**
     * "0012", "金额格式解析错误"
     */
    RESCODE_FORMAT_MONEY_UNRESOLVED("0012", "金额格式解析错误"),
    /**
     * "0013", "其它格式转换错误"
     */
    RESCODE_FORMAT_TRANSFORM_ERROR("0013", "其它格式转换错误"),
    /**
     * "0014", "数据库操作异常"
     */
    RESCODE_DB_ERROR("0014", "数据库操作异常"),
    /**
     * "0015", "IO操作异常"
     */
    RESCODE_IO_ERROR("0015", "IO操作异常"),
    /**
     * "0016", "消息服务异常"
     */
    RESCODE_MQ_ERROR("0016", "消息服务异常"),
    /**
     * "0017", "其它系统异常"
     */
    RESCODE_OTHER_ERROR("0017", "其它系统异常"),
    /**
     * "0018", "访问此资源需要令牌验证"
     */
    RESCODE_NO_TOKEN_ERROR("0018", "访问此资源需要令牌验证"),
    /**
     * "0019", "用户名或密码错误"
     */
    RESCODE_NOT_FOUND_USER("0019", "用户名或密码错误"),
    /**
     * "0020", "密码错误"
     */
    RESCODE_ERROR_PASSWORD("0020", "密码错误"),
    /**
     * "8888", "系统服务异常，熔断请求"
     */
    RESCODE_SYSTEM_HYSTRIC("8888", "系统服务异常，熔断请求"),
    /**
     * "9999", "系统服务异常，请求失败"
     */
    RESCODE_SYSTEM_ERROR("9999", "系统服务异常，请求失败");


    private String statusName;
    private String statusCode;

    ResCodeEnum(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    /**
     * 成功
     *
     * @return
     */
    public static String OK = ResCodeEnum.RESCODE_SUCCESS.getStatusCode();
    public static String OK_NAME = ResCodeEnum.RESCODE_SUCCESS.getStatusName();
    /**
     * 失败
     */
    public static String FAIL = ResCodeEnum.RESCODE_OTHER_ERROR.getStatusCode();
    public static String FAIL_NAME = ResCodeEnum.RESCODE_OTHER_ERROR.getStatusName();


    /**
     * 通过statusCode获取statusName
     *
     * @param statusCode
     * @return
     */
    public static String getResCodeEnum(String statusCode) {
        ResCodeEnum[] values = ResCodeEnum.values();
        for (ResCodeEnum value : values) {
            if (Objects.equals(value.statusCode, statusCode)) {
                return value.statusName;
            }
        }
        return null;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
