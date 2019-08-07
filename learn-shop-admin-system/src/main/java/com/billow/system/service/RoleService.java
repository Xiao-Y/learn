package com.billow.system.service;

import com.billow.system.pojo.ex.DataDictionaryEx;
import com.billow.system.service.query.SelectRoleQuery;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    /**
     * 通过用户id 查询用户角色信息
     *
     * @param userId 用户id
     * @return java.util.List<RoleVo>
     * @author LiuYongTao
     * @date 2018/11/5 16:19
     */
    List<RoleVo> findRoleListInfoByUserId(Long userId);

    /**
     * 根据条件查询角色列表信息
     *
     * @param roleVo
     * @return
     */
    Page<RolePo> findRoleByCondition(RoleVo roleVo) throws Exception;

    /**
     * 根据角色ID查询权限ID
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long>
     * @author LiuYongTao
     * @date 2019/7/12 11:37
     */
    List<Long> findPermissionByRoleId(Long roleId) throws Exception;

    /**
     * 根据角色ID查询菜单ID
     *
     * @param roleId
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/7/12 15:17
     */
    List<String> findMenuByRoleId(Long roleId) throws Exception;

    /**
     * 保存角色信息、角色菜单和角色权限
     *
     * @param roleVo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/12 17:50
     */
    void saveRole(RoleVo roleVo);

    /**
     * 根据id禁用角色信息
     *
     * @param roleId
     * @return com.billow.system.pojo.vo.RoleVo
     * @author LiuYongTao
     * @date 2019/7/30 9:56
     */
    RoleVo prohibitRoleById(Long roleId);

    /**
     * 根据id删除角色信息
     *
     * @param roleId
     * @return com.billow.system.pojo.vo.RoleVo
     * @author LiuYongTao
     * @date 2019/7/30 9:56
     */
    RoleVo deleteRoleById(Long roleId);

    /**
     * 查询角色信息
     *
     * @return java.util.List<com.billow.system.pojo.ex.DataDictionaryEx>
     * @author LiuYongTao
     * @date 2019/7/30 17:19
     */
    List<DataDictionaryEx> findSelectRole();

    /**
     * 根据id查询角色信息
     *
     * @param ids
     * @return com.billow.system.pojo.vo.RoleVo
     * @author LiuYongTao
     * @date 2019/7/31 11:41
     */
    List<RoleVo> findRoleById(List<Long> ids);

    /**
     * 查询 roleCode 的个数
     *
     * @param roleCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/8/7 10:27
     */
    Integer countRoleCodeByRoleCode(String roleCode);
}
