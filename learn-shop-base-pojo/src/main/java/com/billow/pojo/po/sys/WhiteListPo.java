package com.billow.pojo.po.sys;

import com.billow.pojo.po.base.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ip白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:12
 */
@Entity
@Table(name = "sys_white_list")
public class WhiteListPo extends BasePo implements Serializable{
    // ip
    private String ip;
    // 模块
    private String module;
    // 备注
    private String mark;

    public String getIp() {
        return ip;
    }

    public WhiteListPo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getModule() {
        return module;
    }

    public WhiteListPo setModule(String module) {
        this.module = module;
        return this;
    }

    public String getMark() {
        return mark;
    }

    public WhiteListPo setMark(String mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public String toString() {
        return "WhiteListPo{" +
                "ip='" + ip + '\'' +
                ", module='" + module + '\'' +
                ", mark='" + mark + '\'' +
                "} " + super.toString();
    }
}
