package com.billow.pojo.po.sys;

import com.billow.pojo.po.base.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限(菜单)
 *
 * @author liuyongtao
 * @create 2018-05-16 13:57
 */
@Entity
@Table(name = "sys_permission")
public class PermissionPo extends BasePo implements Serializable {
    //权限名称
    private String name;
    //权限描述
    private String descritpion;
    //授权链接
    private String url;
    //父节点id
    private Long pid;

    /**
     * 权限名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 权限名称
     *
     * @param name
     * @return
     */
    public PermissionPo setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 权限描述
     *
     * @return
     */
    public String getDescritpion() {
        return descritpion;
    }

    /**
     * 权限描述
     *
     * @param descritpion
     * @return
     */
    public PermissionPo setDescritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    /**
     * 授权链接
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 授权链接
     *
     * @param url
     * @return
     */
    public PermissionPo setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 父节点id
     *
     * @return
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 父节点id
     *
     * @param pid
     * @return
     */
    public PermissionPo setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public String toString() {
        return "PermissionPo{" +
                "name='" + name + '\'' +
                ", descritpion='" + descritpion + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                '}';
    }
}
