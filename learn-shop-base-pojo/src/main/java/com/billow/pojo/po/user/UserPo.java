package com.billow.pojo.po.user;

import com.billow.pojo.po.base.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "u_user")
public class UserPo extends BasePo implements Serializable {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 加密
    private String salt;
    // 性别
    private String sex;
    // 年龄
    private Integer age;
    /**
     * 用户名
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     *
     * @param username
     * @return
     */
    public UserPo setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * 密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     *
     * @param password
     * @return
     */
    public UserPo setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * 加密
     *
     * @return
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 加密
     *
     * @param salt
     * @return
     */
    public UserPo setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * 性别
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     *
     * @param sex
     * @return
     */
    public UserPo setSex(String sex) {
        this.sex = sex;
        return this;
    }

    /**
     * 年龄
     *
     * @return
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年龄
     *
     * @param age
     * @return
     */
    public UserPo setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "UserPo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
