//package com.billow.common.business.ex;
//
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * 测试
// *
// * @author liuyongtao
// * @create 2018-05-16 10:29
// */
//public class UserEx implements Serializable {
//
//    private Long id;
//    private String creatorCode;
//    private String updaterCode;
//    private Boolean validInd;
//    private Date createTime;
//    private Date updateTime;
//
//    // 用户名
//    private String username;
//    // 密码
//    private String password;
//    // 加密
//    private String salt;
//    // 性别
//    private String sex;
//    // 年龄
//    private Integer age;
//
//    /**
//     * 用户名
//     *
//     * @return
//     */
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * 用户名
//     *
//     * @param username
//     * @return
//     */
//    public UserEx setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    /**
//     * 密码
//     *
//     * @return
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * 密码
//     *
//     * @param password
//     * @return
//     */
//    public UserEx setPassword(String password) {
//        this.password = password;
//        return this;
//    }
//
//    /**
//     * 加密
//     *
//     * @return
//     */
//    public String getSalt() {
//        return salt;
//    }
//
//    /**
//     * 加密
//     *
//     * @param salt
//     * @return
//     */
//    public UserEx setSalt(String salt) {
//        this.salt = salt;
//        return this;
//    }
//
//    /**
//     * 性别
//     *
//     * @return
//     */
//    public String getSex() {
//        return sex;
//    }
//
//    /**
//     * 性别
//     *
//     * @param sex
//     * @return
//     */
//    public UserEx setSex(String sex) {
//        this.sex = sex;
//        return this;
//    }
//
//    /**
//     * 年龄
//     *
//     * @return
//     */
//    public Integer getAge() {
//        return age;
//    }
//
//    /**
//     * 年龄
//     *
//     * @param age
//     * @return
//     */
//    public UserEx setAge(Integer age) {
//        this.age = age;
//        return this;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public UserEx setId(Long id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getCreatorCode() {
//        return creatorCode;
//    }
//
//    public UserEx setCreatorCode(String creatorCode) {
//        this.creatorCode = creatorCode;
//        return this;
//    }
//
//    public String getUpdaterCode() {
//        return updaterCode;
//    }
//
//    public UserEx setUpdaterCode(String updaterCode) {
//        this.updaterCode = updaterCode;
//        return this;
//    }
//
//    public Boolean getValidInd() {
//        return validInd;
//    }
//
//    public UserEx setValidInd(Boolean validInd) {
//        this.validInd = validInd;
//        return this;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public UserEx setCreateTime(Date createTime) {
//        this.createTime = createTime;
//        return this;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public UserEx setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return "UserEx{" +
//                "id=" + id +
//                ", creatorCode='" + creatorCode + '\'' +
//                ", updaterCode='" + updaterCode + '\'' +
//                ", validInd=" + validInd +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", salt='" + salt + '\'' +
//                ", sex='" + sex + '\'' +
//                ", age=" + age +
//                '}';
//    }
//}
