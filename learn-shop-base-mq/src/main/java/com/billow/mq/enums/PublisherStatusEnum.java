package com.billow.mq.enums;

public enum PublisherStatusEnum {
    INIT("0", "初始化"),
    SUCCESS("1", "成功"),
    FAIL("2", "失败");

    private String status;
    private String msg;

    PublisherStatusEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
