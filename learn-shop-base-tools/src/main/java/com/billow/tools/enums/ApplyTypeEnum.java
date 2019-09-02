package com.billow.tools.enums;

public enum ApplyTypeEnum {

    LEAVE("leave", "process_pool");

    private String applyType;
    private String processKey;

    ApplyTypeEnum(String applyType, String processKey) {
        this.applyType = applyType;
        this.processKey = processKey;
    }

    public String getApplyType() {
        return applyType;
    }

    public String getProcessKey() {
        return processKey;
    }

}
