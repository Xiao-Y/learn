package com.billow.tools.enums;

/**
 * 提交类型:submit-提交，reSubmit-重新提交,cancel-取消申请，agree-同意，reject-驳回
 *
 * @author LiuYongTao
 * @date 2019/9/11 8:55
 */
public enum SubmitTypeEnum {

    submit("submit", "1"),
    reSubmit("reSubmit", "1"),
    cancel("cancel", "0"),
    agree("agree", "1"),
    reject("reject", "0");

    private String submitType;
    private String transFlag;

    SubmitTypeEnum(String submitType, String transFlag) {
        this.submitType = submitType;
        this.transFlag = transFlag;
    }

    /**
     * 通过 submitType 获取 SubmitTypeEnum
     *
     * @param submitType
     * @return com.billow.tools.enums.SubmitTypeEnum
     * @author LiuYongTao
     * @date 2019/9/11 9:00
     */
    public static SubmitTypeEnum getSubmitTypeEnum(String submitType) {
        SubmitTypeEnum[] values = SubmitTypeEnum.values();
        for (SubmitTypeEnum value : values) {
            if (value.getSubmitType().equals(submitType)) {
                return value;
            }
        }
        return null;
    }

    public String getSubmitType() {
        return submitType;
    }

    public String getTransFlag() {
        return transFlag;
    }
}
