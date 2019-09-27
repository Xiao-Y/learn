package com.billow.job.core.enumType;

/**
 * 自动任务-任务状态
 */
public enum AutoTaskJobStatusEnum {

    /**
     * 任务状态,0-停止
     */
    JOB_STATUS_PAUSE("0", "停止"),
    /**
     * 任务状态,1-启用
     */
    JOB_STATUS_RESUME("1", "启用"),
    /**
     * 任务状态,2-删除
     */
    JOB_STATUS_DELETE("2", "删除"),
    /**
     * 任务状态,3-异常
     */
    JOB_STATUS_EXCEPTION("3", "异常");

    String status;
    String statusName;

    AutoTaskJobStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    /**
     * 通过状态获取名称
     *
     * @param status
     * @return
     */
    public static String getStatusNameByStatus(String status) {
        for (AutoTaskJobStatusEnum c : AutoTaskJobStatusEnum.values()) {
            if (c.getStatus().equals(status)) {
                return c.statusName;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
