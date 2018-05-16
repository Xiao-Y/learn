package com.billow.pojo;

/**
 * @author Levin
 * @date 2017-08-15.
 */
public class UserInfo {

    private String userName;
    private String password;

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

    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
