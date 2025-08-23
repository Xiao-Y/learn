package com.billow.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.common.utils.UserTools;
import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.build.UserBuildParam;
import com.billow.system.pojo.po.UserPo;
import com.billow.system.pojo.search.UserSearchParam;
import com.billow.system.pojo.vo.UserVo;
import com.billow.system.service.UserService;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:11
 */
@Tag(name = "UserApi", description = "用户信息操作")
@RestController
@RequestMapping("/userApi")
public class UserApi extends HighLevelApi<UserService, UserPo, UserVo,
        UserBuildParam, UserSearchParam> {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTools userTools;

    @Operation(summary = "根据条件查询用户信息")
    @PostMapping("/findUserList")
    public IPage<UserVo> findUserList(@RequestBody UserSearchParam userVo) {
        return userService.findUserList(userVo);
    }

    @Operation(summary = "保存用户信息")
    @PostMapping("/saveUser")
    public UserVo saveUser(@RequestBody UserVo userVo) {
        return userService.saveUser(userVo);
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/updateUser")
    public UserVo updateUser(@RequestBody UserVo userVo) {
        return userService.saveUser(userVo);
    }

    @Operation(summary = "根据id禁用用户信息")
    @DeleteMapping("/prohibitUserById/{id}")
    public UserVo prohibitUserById(@PathVariable("id") Long id) {
        return userService.prohibitUserById(id);
    }

    @Operation(summary = "根据id删除用户信息")
    @DeleteMapping("/deleteUserById/{id}")
    public UserVo deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    @Operation(summary = "根据id查询有效的用户角色")
    @GetMapping("/findRoleIdsByUserId/{id}")
    public UserVo findRoleIdsByUserId(@PathVariable("id") Long id) {
        return userService.findRoleIdsByUserId(id);
    }

    @Operation(summary = "根据登陆token查询用户信息")
    @GetMapping("/getUserInfo")
    public UserVo getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "根据userId查询用户信息")
    @GetMapping("/findUserInfoById/{id}")
    public UserPo findUserInfoById(@PathVariable("id")  Long id) {
        return userService.findUserInfoById(id);
    }

    @Operation(summary = "查询 userCode 的个数")
    @GetMapping("/checkUserCode/{userCode}")
    public Integer checkUserCode(@PathVariable("userCode") String userCode) {
        return userService.checkUserCode(userCode);
    }

    @Operation(summary = "修改密码")
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

    @Operation(summary = "修改用户头像")
    @PutMapping("/updateUserIcon")
    public UserVo updateUserIcon(@RequestBody UserVo userVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        userVo.setUsercode(currentUserCode);
        userService.updateUserIcon(userVo);
        return userVo;
    }
}
