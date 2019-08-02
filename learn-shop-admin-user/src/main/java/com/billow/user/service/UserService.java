package com.billow.user.service;

import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.vo.UserVo;
import org.springframework.data.domain.Page;

public interface UserService {

//    /**
//     * 根据userCode 查询出用户信息
//     *
//     * @param [userCode]
//     * @return UserVo
//     * @author LiuYongTao
//     * @date 2018/11/5 16:04
//     */
//    UserVo findUserInfoByUsercode(String userCode);
//
//    UserDetails loadUserByUsername(String userCode);

    /**
     * 根据条件查询用户信息
     *
     * @param userVo
     * @return org.springframework.data.domain.Page<com.billow.user.pojo.po.UserPo>
     * @author LiuYongTao
     * @date 2019/7/30 13:48
     */
    Page<UserPo> findUserList(UserVo userVo);

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
}
