package com.billow.auth.pojo.security;

/**
 * 用户信息
 *
 * @author liuyongtao
 * @create 2019-04-30 11:23
 */
public class User {
    // 用户名
    private String username;
    // 用户code
    private String usercode;
    // 密码
    private String password;
    // 性别
    private String sex;
    // 年龄
    private Integer age;
    // 是否有效
    private boolean validInd;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsercode() {
        return usercode;
    }

    public User setUsercode(String usercode) {
        this.usercode = usercode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public boolean getValidInd() {
        return validInd;
    }

    public User setValidInd(boolean validInd) {
        this.validInd = validInd;
        return this;
    }
}
