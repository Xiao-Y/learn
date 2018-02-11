package com.ft.enums;

import java.util.Objects;

/**
 * 用于标识事务处理的状态
 */
public enum SysEventEunm {

    status_new("NEW", "新事件"), status_published("PUBLISHED", "新事件");

    private String statusCode;
    private String statusName;

    SysEventEunm(String statusCode, String statusName) {
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
        SysEventEunm[] values = SysEventEunm.values();
        for (SysEventEunm value : values) {
            if (Objects.equals(value.statusCode, statusCode)) {
                return value.statusName;
            }
        }
        return null;
    }
}
