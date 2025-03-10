//package com.billow.excel.test.model;
//
//import com.billow.excel.annotation.ExcelColumn;
//import com.billow.excel.annotation.ExcelSheet;
//import lombok.Data;
//import lombok.experimental.Accessors;
//
//import java.util.Date;
//
///**
// * 用户Excel导入导出模型
// */
//@Data
//@Accessors(chain = true)
//@ExcelSheet(name = "用户信息")
//public class UserExcelModel {
//
//    @ExcelColumn(name = "用户ID", order = 0)
//    private Long userId;
//
//    @ExcelColumn(name = "用户名", order = 1, required = true)
//    private String username;
//
//    @ExcelColumn(name = "昵称", order = 2)
//    private String nickname;
//
//    @ExcelColumn(name = "性别", order = 3, dictCode = "USER_GENDER")
//    private String gender;
//
//    @ExcelColumn(name = "状态", order = 4, dictCode = "USER_STATUS")
//    private String status;
//
//    @ExcelColumn(name = "年龄", order = 5)
//    private Integer age;
//
//    @ExcelColumn(name = "邮箱", order = 6)
//    private String email;
//
//    @ExcelColumn(name = "手机号", order = 7)
//    private String mobile;
//
//    @ExcelColumn(name = "创建时间", order = 8, format = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//    @ExcelColumn(name = "更新时间", order = 9, format = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;
//}