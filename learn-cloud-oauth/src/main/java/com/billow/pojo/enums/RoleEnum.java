package com.billow.pojo.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private String desc;

    public String desc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RoleEnum(String desc) {
        this.desc = desc;
    }
}

