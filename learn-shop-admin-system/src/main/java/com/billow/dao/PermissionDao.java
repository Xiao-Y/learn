package com.billow.dao;

import com.billow.pojo.po.sys.PermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionDao extends JpaRepository<PermissionPo, Long> {

    /**
     * 查询出父级菜单
     *
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:11
     */
    List<PermissionPo> findByPidIsNull();

    /**
     * 根据父级菜单id查询出子级菜单
     *
     * @param pid 父级菜单id
     * @return java.util.List<com.billow.pojo.po.sys.PermissionPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:12
     */
    List<PermissionPo> findByPidEquals(Long pid);
}
