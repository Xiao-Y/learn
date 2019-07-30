package com.billow.user.api;

import com.billow.common.base.BaseApi;
import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.vo.UserVo;
import com.billow.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:11
 */
@Api(value = "用户信息操作")
@RestController
@RequestMapping("/userApi")
public class UserApi extends BaseApi {

    @Autowired
    private UserService userService;

    @ApiOperation("根据条件查询用户信息")
    @PostMapping("/findUserList")
    public Page<UserPo> findUserList(@RequestBody UserVo userVo) {
        return userService.findUserList(userVo);
    }

    @ApiOperation("保存用户信息")
    @PostMapping("/saveUser")
    public UserVo saveUser(@RequestBody UserVo userVo) {
        return userService.saveUser(userVo);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/updateUser")
    public UserVo updateUser(@RequestBody UserVo userVo) {
        return userService.saveUser(userVo);
    }

    @ApiOperation("根据id禁用用户信息")
    @DeleteMapping("/prohibitUserById/{id}")
    public UserVo prohibitUserById(@PathVariable("id") Long id) {
        return userService.prohibitUserById(id);
    }

    @ApiOperation("根据id删除用户信息")
    @DeleteMapping("/deleteUserById/{id}")
    public UserVo deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }


//    /**
//     * 根据用户名查询出用户信息
//     *
//     * @param userCode 用户名
//     * @return UserVo
//     * @author LiuYongTao
//     * @date 2018/11/5 15:18
//     */
//    @GetMapping("/findUserInfoByUsercode/{userCode}")
//    public UserVo findUserInfoByUsercode(@PathVariable("userCode") String userCode) {
//        UserVo userVo = userService.findUserInfoByUsercode(userCode);
//        return userVo;
//    }
//
//    /**
//     * 根据用户code 查询用户信息，用于spring security 认证使用
//     *
//     * @param userCode 用户code
//     * @return org.springframework.security.core.userdetails.UserDetails
//     * @author LiuYongTao
//     * @date 2019/4/28 9:27
//     */
//    @GetMapping("/loadUserByUsername/{userCode}")
//    public UserDetails loadUserByUsername(@PathVariable("userCode") String userCode) {
//        UserDetails userDetails = userService.loadUserByUsername(userCode);
//        return userDetails;
//    }
}
