package com.billow.tools.enums;

import java.util.Objects;

/**
 * 事务类型
 */
public enum SysEventTypeEunm {

    event_type_orderToUser_test("orderToUser_test", "订单-用户-测试");

    private String statusCode;
    private String statusName;

    SysEventTypeEunm(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    /**
     * 通过statusCode获取statusName
     *
     * @param statusCode
     * @return
     */
    public static String getStatusNameByStatusCode(String statusCode) {
        SysEventTypeEunm[] values = SysEventTypeEunm.values();
        for (SysEventTypeEunm value : values) {
            if (Objects.equals(value.statusCode, statusCode)) {
                return value.statusName;
            }
        }
        return null;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}

