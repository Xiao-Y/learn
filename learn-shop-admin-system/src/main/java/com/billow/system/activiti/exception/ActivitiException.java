package com.billow.system.activiti.exception;

/**
 * @author liuyongtao
 * @create 2019-09-01 12:48
 */
public class ActivitiException extends RuntimeException {

    public ActivitiException() {
        super();
    }

    /**
     * 不允许通过 activiti
     *
     * @param message
     * @author billow
     * @date 2019/9/1 12:59
     */
    public ActivitiException(String message) {
        super("不允许通过 activiti " + message);
    }
}
