package com.billow.system.service.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 操作 redis 中的角色和权限数据
 *
 * @author liuyongtao
 * @create 2019-07-16 15:05
 */
@Service
public class CommonRolePermissionRedis {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 更新角色CODE
     *
     * @param newRoleCode
     * @param oldRoleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:45
     */
    public void updateRoleCode(String newRoleCode, String oldRoleCode) {
        if (newRoleCode.equals(oldRoleCode)) {
            return;
        }
        List<PermissionVo> permissionVos = redisUtils.getArray("ROLE:" + oldRoleCode, PermissionVo.class);
        this.deleteRoleByRoleCode(oldRoleCode);
        redisUtils.setObj("ROLE:" + newRoleCode, permissionVos);
    }

    /**
     * 删除指定角色的权限信息
     *
     * @param roleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:09
     */
    public void deleteRoleByRoleCode(String roleCode) {
        redisTemplate.delete("ROLE:" + roleCode);
    }

    /**
     * 删除 redis 所有角色所持有的该权限
     *
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:02
     */
    public void deleteRolePermissionById(Long id) {
        Set<String> roleKeys = redisTemplate.keys("ROLE:*");
        roleKeys.stream().forEach(f -> {
            List<PermissionVo> permissionVos = redisUtils.getArray(f, PermissionVo.class);
            List<PermissionVo> voList = permissionVos.stream().filter(fi -> !fi.getId().equals(id)).collect(Collectors.toList());
            redisUtils.setObj(f, voList);
        });
    }

    /**
     * 通过权限 id 更新权限信息
     *
     * @param permissionVo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:46
     */
    public void updatePermissionById(PermissionVo permissionVo) {
        Set<String> roleKeys = redisTemplate.keys("ROLE:*");
        roleKeys.stream().forEach(f -> {
            List<PermissionVo> permissionVos = redisUtils.getArray(f, PermissionVo.class);
            List<PermissionVo> voList = permissionVos.stream().filter(fi -> !fi.getId().equals(permissionVo.getId())).collect(Collectors.toList());
            voList.add(permissionVo);
            redisUtils.setObj(f, voList);
        });
    }

    /**
     * 更新指定角色的权限信息
     *
     * @param permissionPos
     * @param roleCode
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 16:57
     */
    public void updateRolePermissionByRoleCode(List<PermissionPo> permissionPos, String roleCode) {
        redisUtils.setObj("ROLE:" + roleCode, permissionPos);
    }
}
