package com.billow.user.service;

import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.vo.UserVo;
import org.springframework.data.domain.Page;

public interface UserService {

    /**
     * 根据条件查询用户信息
     *
     * @param userVo
     * @return org.springframework.data.domain.Page<com.billow.user.pojo.vo.UserVo>
     * @author LiuYongTao
     * @date 2019/8/5 13:51
     */
    Page<UserVo> findUserList(UserVo userVo);

    /**
     * 保存用户信息
     *
     * @param userVo
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/7/30 13:59
     */
    UserVo saveUser(UserVo userVo);

    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/7/30 14:06
     */
    UserVo deleteUserById(Long id);

    /**
     * 根据id禁用用户信息
     *
     * @param id
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/7/30 14:08
     */
    UserVo prohibitUserById(Long id);

    /**
     * 根据id查询用户角色
     *
     * @param id
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/7/30 18:08
     */
    UserVo findRoleIdsByUserId(Long id);

    /**
     * 根据 token 查询用户信息
     *
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/8/2 14:16
     */
    UserVo getUserInfo();

    /**
     * 查询 userCode 的个数
     *
     * @param userCode
     * @return java.lang.Integer
     * @author billow
     * @date 2019/8/3 15:47
     */
    Integer checkUserCode(String userCode);

    /**
     * 校验用户的密码是否正确,如果正确则返回用户信息，否则返回空
     *
     * @param currentUserCode
     * @param oldPassWord
     * @return com.billow.user.pojo.vo.UserVo
     * @author billow
     * @date 2019/8/3 18:21
     */
    UserVo checkPassWord(String currentUserCode, String oldPassWord);

    /**
     * 更新密码
     *
     * @param oldUser
     * @return void
     * @author billow
     * @date 2019/8/3 18:24
     */
    void editPassWord(UserVo oldUser);

    /**
     * 修改用户头像
     *
     * @param userVo
     * @return java.lang.Boolean
     * @author LiuYongTao
     * @date 2019/8/8 14:27
     */
    Boolean updateUserIcon(UserVo userVo);

    /**
     * 根据userId查询用户信息
     *
     * @param id
     * @return com.billow.user.pojo.po.UserPo
     * @author billow
     * @date 2019/9/1 13:20
     */
    UserPo findUserInfoById(Long id);

}
