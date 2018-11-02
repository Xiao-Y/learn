package com.billow.dao;

import com.billow.pojo.po.PermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionDao extends JpaRepository<PermissionPo, Long> {

    /**
     * 查询出父级菜单（所有）
     *
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:11
     */
    List<PermissionPo> findByPidIsNull();

    /**
     * 查询出父级菜单（有效）
     *
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:11
     */
    List<PermissionPo> findByPidIsNullAndValidIndIsTrue();


    /**
     * 根据父级菜单id查询出子级菜单（所有）
     *
     * @param pid 父级菜单id
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:12
     */
    List<PermissionPo> findByPidEquals(Long pid);

    /**
     * 根据父级菜单id查询出子级菜单（有效）
     *
     * @param pid 父级菜单id
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:12
     */
    List<PermissionPo> findByPidEqualsAndValidIndIsTrue(Long pid);
}
