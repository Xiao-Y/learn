package com.billow.base.workflow.vo;

import java.util.Date;

/**
 * 批注信息
 *
 * @author liuyongtao
 * @create 2019-09-08 10:58
 */
public class CommentVo {

    private String id;
    private String userId;
    private String taskId;
    private String processInstanceId;
    private String type;
    private Date time;
    private String action;
    private String message;
    private String fullMessage;

    public String getType() {
        return type;
    }

    public CommentVo setType(String type) {
        this.type = type;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CommentVo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public CommentVo setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public CommentVo setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public CommentVo setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getAction() {
        return action;
    }

    public CommentVo setAction(String action) {
        this.action = action;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommentVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public CommentVo setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
        return this;
    }
}
