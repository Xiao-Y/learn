package com.billow.zuul.pojo;

/**
 * @author Levin
 * @date 2017-08-15.
 */
public class UserRole {

    private String roleName;


    public UserRole() {
    }

    public UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String authority() {
        return this.getRoleName();
    }
}
