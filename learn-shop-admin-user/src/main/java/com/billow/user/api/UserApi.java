package com.billow.user.api;

import com.billow.common.base.BaseApi;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.UserTools;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private UserTools userTools;

    @ApiOperation("根据条件查询用户信息")
    @PostMapping("/findUserList")
    public Page<UserVo> findUserList(@RequestBody UserVo userVo) {
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

    @ApiOperation("根据id查询有效的用户角色")
    @GetMapping("/findRoleIdsByUserId/{id}")
    public UserVo findRoleIdsByUserId(@PathVariable("id") Long id) {
        return userService.findRoleIdsByUserId(id);
    }

    @ApiOperation("根据登陆token查询用户信息")
    @GetMapping("/getUserInfo")
    public UserVo getUserInfo() {
        return userService.getUserInfo();
    }

    @ApiOperation("根据userId查询用户信息")
    @GetMapping("/findUserInfoById")
    public UserPo findUserInfoById(@RequestParam Long id) {
        return userService.findUserInfoById(id);
    }

    @ApiOperation(value = "查询 userCode 的个数")
    @GetMapping("/checkUserCode/{userCode}")
    public Integer checkUserCode(@PathVariable("userCode") String userCode) {
        return userService.checkUserCode(userCode);
    }

    @ApiOperation("修改密码")
    @PutMapping("/editPassWord")
    public BaseResponse<Boolean> editPassWord(@RequestBody UserVo userVo) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();

        String currentUserCode = userTools.getCurrentUserCode();
        UserVo oldUser = userService.checkPassWord(currentUserCode, userVo.getOldPassWord());
        if (oldUser == null) {
            baseResponse.setResCode(ResCodeEnum.RESCODE_ERROR_PASSWORD);
            return baseResponse;
        }
        oldUser.setPassword(userVo.getNewPassWord());
        userService.editPassWord(oldUser);
        return baseResponse;
    }

    @ApiOperation("修改用户头像")
    @PutMapping("/updateUserIcon")
    public UserVo updateUserIcon(@RequestBody UserVo userVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        userVo.setUsercode(currentUserCode);
        userService.updateUserIcon(userVo);
        return userVo;
    }
}
