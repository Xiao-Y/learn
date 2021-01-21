package com.billow.jpa.base.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@MappedSuperclass
public class BasePoDefault extends BasePage implements Serializable {

    @Id
    private String id;
    private String creatorCode;
    private String updaterCode;
    private Boolean validInd;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public BasePoDefault setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public BasePoDefault setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
        return this;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public BasePoDefault setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
        return this;
    }

    public Boolean getValidInd() {
        return validInd;
    }

    public BasePoDefault setValidInd(Boolean validInd) {
        this.validInd = validInd;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BasePoDefault setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BasePoDefault setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "BasePo{" +
                "id=" + id +
                ", creatorCode='" + creatorCode + '\'' +
                ", updaterCode='" + updaterCode + '\'' +
                ", validInd=" + validInd +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "} " + super.toString();
    }
}