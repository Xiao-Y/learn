package com.billow.product.pojo.re;


import java.io.Serializable;

/**
 * @author liuyongtao
 * @create 2018-05-19 14:31
 */
public class WhiteListRe implements Serializable {

    // ip
    private String ip;
    // 模块
    private String module;
    // 端口
    private String port;
    // 备注
    private String mark;

    public String getPort() {
        return port;
    }

    public WhiteListRe setPort(String port) {
        this.port = port;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public WhiteListRe setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getModule() {
        return module;
    }

    public WhiteListRe setModule(String module) {
        this.module = module;
        return this;
    }

    public String getMark() {
        return mark;
    }

    public WhiteListRe setMark(String mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public String toString() {
        return "WhiteListVo{" +
                "ip='" + ip + '\'' +
                ", module='" + module + '\'' +
                ", port='" + port + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
