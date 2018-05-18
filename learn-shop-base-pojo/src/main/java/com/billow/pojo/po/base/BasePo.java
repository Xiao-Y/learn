package com.billow.pojo.po.base;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-04-27 12:28
 */
@MappedSuperclass
public class BasePo extends BasePage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String creatorCode;
    private String updaterCode;
    private Boolean validInd;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public BasePo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public BasePo setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
        return this;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public BasePo setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
        return this;
    }

    public Boolean getValidInd() {
        return validInd;
    }

    public BasePo setValidInd(Boolean validInd) {
        this.validInd = validInd;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BasePo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BasePo setUpdateTime(Date updateTime) {
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