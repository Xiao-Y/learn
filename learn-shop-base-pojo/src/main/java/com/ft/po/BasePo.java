package com.ft.po;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@MappedSuperclass
public class BasePo extends BasePage {

    private String creatorCode;
    private Date createTime;
    private String updaterCode;
    private Date updateTime;
    private Boolean validInd;

    public Boolean getValidInd() {
        return validInd;
    }

    public void setValidInd(Boolean validInd) {
        this.validInd = validInd;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public void setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BasePo{" +
                "creatorCode='" + creatorCode + '\'' +
                ", createTime=" + createTime +
                ", updaterCode='" + updaterCode + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}