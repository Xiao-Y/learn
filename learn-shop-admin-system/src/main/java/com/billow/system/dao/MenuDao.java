package com.billow.system.dao;

import com.billow.system.pojo.po.MenuPo;
import com.billow.tools.utlis.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuDao extends JpaRepository<MenuPo, Long> {

    /**
     * 查询出父级菜单（所有）
     *
     * @return java.util.List<com.billow.pojo.po.sys.MenuPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:11
     */
    List<MenuPo> findByPidIsNull();

    /**
     * 查询出父级菜单（有效）
     *
     * @return java.util.List<com.billow.pojo.po.sys.MenuPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:11
     */
    List<MenuPo> findByPidIsNullAndValidIndIsTrue();


    /**
     * 根据父级菜单id查询出子级菜单（所有）
     *
     * @param pid 父级菜单id
     * @return java.util.List<com.billow.pojo.po.sys.MenuPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:12
     */
    List<MenuPo> findByPidEquals(Long pid);

    /**
     * 根据父级菜单id查询出子级菜单（有效）
     *
     * @param pid 父级菜单id
     * @return java.util.List<com.billow.pojo.po.sys.MenuPo>
     * @author LiuYongTao
     * @date 2018/5/26 10:12
     */
    List<MenuPo> findByPidEqualsAndValidIndIsTrue(Long pid);

    /**
     * 查询 menuCode 的个数
     *
     * @param menuCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/7/24 14:16
     */
    Integer countByMenuCodeIsAndValidIndIsTrue(String menuCode);
}
