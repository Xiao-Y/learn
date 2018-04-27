package com.ft.po;

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

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

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