package com.billow.zuul.pojo.re;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-11-06 9:39
 */
public class UserRe {
    // 用户名
    private String username;
    // 用户code
    private String usercode;
    // 密码
    private String password;
    // 加密
    private String salt;
    // 性别
    private String sex;
    // 年龄
    private Integer age;
    private Long id;
    private String creatorCode;
    private String updaterCode;
    private Boolean validInd;
    private Date createTime;
    private Date updateTime;

    public String getUsername() {
        return username;
    }

    public UserRe setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsercode() {
        return usercode;
    }

    public UserRe setUsercode(String usercode) {
        this.usercode = usercode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRe setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public UserRe setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public UserRe setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserRe setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserRe setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public UserRe setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
        return this;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public UserRe setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
        return this;
    }

    public Boolean getValidInd() {
        return validInd;
    }

    public UserRe setValidInd(Boolean validInd) {
        this.validInd = validInd;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public UserRe setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public UserRe setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "UserRe{" +
                "username='" + username + '\'' +
                ", usercode='" + usercode + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", creatorCode='" + creatorCode + '\'' +
                ", updaterCode='" + updaterCode + '\'' +
                ", validInd=" + validInd +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
