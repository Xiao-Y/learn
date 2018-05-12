package com.ft.enums;

import java.util.Objects;

/**
 * 状态码
 */
public enum ResCodeEnum {

    RESCODE_SUCCESS("0000", "成功"),
    RESCODE_BAD_REQUEST("0001", "报文不可解析"),
    RESCODE_BAD_REQHEADER("0002", "报文头错误"),
    RESCODE_BAD_REQBODY("0003", "报文体格式错误"),
    RESCODE_SERVICE_UNREACHABLE("0004", "当前服务不可用"),
    RESCODE_SIGNATURE_ERROR("0005", "签名校验错误"),
    RESCODE_UNAUTHORIZED("0006", "权限校验未通过"),
    RESCODE_NULL_RESULT("0007", "无法查询到相关信息或查询结果为空"),
    RESCODE_TIMEOUT("0008", "网络超时"),
    RESCODE_FORBIDDEN("0009", "无相关权限"),
    RESCODE_RULE_UNMATCH("0010", "不符合相关业务规则"),
    RESCODE_FORMAT_DATETIME_UNRESOLVED("0011", "日期格式解析错误"),
    RESCODE_FORMAT_MONEY_UNRESOLVED("0012", "金额格式解析错误"),
    RESCODE_FORMAT_TRANSFORM_ERROR("0013", "其它格式转换错误"),
    RESCODE_DB_ERROR("0014", "数据库操作异常"),
    RESCODE_IO_ERROR("0015", "IO操作异常"),
    RESCODE_MQ_ERROR("0016", "消息服务异常"),
    RESCODE_OTHER_ERROR("0017", "其它系统异常"),
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
