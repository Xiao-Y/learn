package com.billow.job.core.enumType;

/**
 * 自动任务,是否有状态
 */
public enum AutoTaskJobConcurrentEnum {
    /**
     * 是否有状态的,0-否（相当于多线程）
     */
    CONCURRENT_NOT("0", "否"),
    /**
     * 是否有状态的，1-是（相当于单线程）
     */
    CONCURRENT_IS("1", "是");

    String isConcurrent;
    String isConcurrentName;

    AutoTaskJobConcurrentEnum(String isConcurrent, String isConcurrentName) {
        this.isConcurrent = isConcurrent;
        this.isConcurrentName = isConcurrentName;
    }

    /**
     * 通过状态获取名称
     *
     * @param isConcurrent
     * @return
     */
    public static String getIsConcurrentNameByIsConcurrent(String isConcurrent) {
        for (AutoTaskJobConcurrentEnum c : AutoTaskJobConcurrentEnum.values()) {
            if (c.getIsConcurrent().equals(isConcurrent)) {
                return c.isConcurrentName;
            }
        }
        return null;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getIsConcurrentName() {
        return isConcurrentName;
    }

    public void setIsConcurrentName(String isConcurrentName) {
        this.isConcurrentName = isConcurrentName;
    }
}
