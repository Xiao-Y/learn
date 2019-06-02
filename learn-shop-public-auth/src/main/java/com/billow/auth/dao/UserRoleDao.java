package com.billow.auth.dao;

import com.billow.auth.pojo.po.UserRolePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDao extends JpaRepository<UserRolePo, Long> {

    /**
     * 通过用户id 查询出角色id
     *
     * @param userId 用户id
     * @return java.util.List<UserRolePo>
     * @author LiuYongTao
     * @date 2018/11/5 16:23
     */
    List<UserRolePo> findRoleIdByUserIdAndValidIndIsTrue(Long userId);
}
