package com.billow.pojo.po;

import java.io.Serializable;

/**
 * 权限
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
public class Permission extends BasePo implements Serializable {
    //权限名称
    private String name;
    //权限描述
    private String descritpion;
    //授权链接
    private String url;
    //父节点id
    private Long pid;

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public Permission setDescritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }

    public Long getPid() {
        return pid;
    }

    public Permission setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", descritpion='" + descritpion + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                "} " + super.toString();
    }
}
