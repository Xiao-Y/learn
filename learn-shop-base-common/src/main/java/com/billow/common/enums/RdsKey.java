package com.billow.common.enums;

/**
 * redis 的key
 *
 * @author LiuYongTao
 * @date 2018/5/24 9:52
 */
public enum RdsKey {

    /**
     * "whiteList:", "白名单前缀"
     */
    WHITE_LIST("whiteList:", "白名单前缀");

    private String key;
    private String value;

    RdsKey(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public RdsKey setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RdsKey setValue(String value) {
        this.value = value;
        return this;
    }
}
