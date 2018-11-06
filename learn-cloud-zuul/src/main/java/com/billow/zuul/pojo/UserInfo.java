package com.billow.zuul.pojo;

/**
 * @author Levin
 * @date 2017-08-15.
 */
public class UserInfo {

    private Long userId;
    private String userName;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public UserInfo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo() {
    }

    public UserInfo(Long userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
