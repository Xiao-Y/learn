package com.ft.sysEvent.model.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 夸系统事务运行操作,记录待处理事件
 *
 * @author liuyongtao
 * @create 2018-02-09 15:03
 */
@MappedSuperclass
public class SysEventProcessBase {

    @Id
    private String id; //与发布事件的id相同
    //事件状态, 枚举类型. 现在只有两个状态: 待处理(NEW), 已处理(PUBLISHED)
    private String status;
    //处理事件的服务器ip
    private String ip;
    //处理事件的类名和处理方法名
    private String className;
    //事件类型
    private String eventType;
    //事件内容
    private String payload;
    //事件创建时候
    private Date createDate;
    //事件修改时间
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "SysEventProcessBase{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", ip='" + ip + '\'' +
                ", className='" + className + '\'' +
                ", eventType='" + eventType + '\'' +
                ", payload='" + payload + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
