//package com.billow.excel.test.enums;
//
//import com.billow.excel.service.impl.EnumDictProvider.DictEnum;
//
///**
// * 用户状态枚举
// */
//public enum UserStatusEnum implements DictEnum {
//    NORMAL("1", "正常"),
//    DISABLED("2", "禁用"),
//    DELETED("3", "已删除");
//
//    private final String value;
//    private final String label;
//
//    UserStatusEnum(String value, String label) {
//        this.value = value;
//        this.label = label;
//    }
//
//    @Override
//    public String getValue() {
//        return value;
//    }
//
//    @Override
//    public String getLabel() {
//        return label;
//    }
//}