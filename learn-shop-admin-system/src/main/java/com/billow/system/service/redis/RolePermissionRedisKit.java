package com.billow.system.service.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 操作 redis 中的角色和权限数据
 *
 * @author liuyongtao
 * @create 2019-07-16 15:05
 */
@Service
public class RolePermissionRedisKit {

    public final static String ROLE_PERMISSION_KEY = RedisCst.ROLE_PERMISSION_KEY;

    @Autowired
    private RedisUtils redisUtils;

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
        if (Objects.equals(newRoleCode, oldRoleCode)) {
            return;
        }
        // 先取出旧值，再删除
        String json = redisUtils.getHashObj(ROLE_PERMISSION_KEY, oldRoleCode, String.class);
        this.deleteRoleByRoleCode(oldRoleCode);
        // 设置角色的权限信息
        redisUtils.setHash(ROLE_PERMISSION_KEY, newRoleCode, json);
    }

    /**
     * 删除 redis 所有角色所持有的该权限
     *
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:02
     */
    public void deleteRolePermissionById(Long id) {
        Map<String, List<PermissionVo>> rolePermissionMap = this.getRolePermissionMap();
        rolePermissionMap.entrySet().stream().forEach(f -> {
            List<PermissionVo> permissionVos = f.getValue();
            List<PermissionVo> voList = permissionVos.stream()
                    .filter(fi -> !fi.getId().equals(id))
                    .map(m -> ConvertUtils.convertIgnoreBase(m, PermissionVo.class))
                    .collect(Collectors.toList());
            this.setRolePermission(ConvertUtils.convertIgnoreBase(voList, PermissionPo.class), f.getKey());
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
        // 取出缓存中数据
        Map<String, List<PermissionVo>> hashAll = this.getRolePermissionMap();
        hashAll.entrySet().stream().forEach(f -> {
            List<PermissionVo> permissionVos = f.getValue();
            // 移除旧权限信息
            List<PermissionPo> voList = permissionVos.stream()
                    .filter(fi -> !fi.getId().equals(permissionVo.getId()))
                    .map(m -> ConvertUtils.convertIgnoreBase(m, PermissionPo.class))
                    .collect(Collectors.toList());
            // 把新的权限信息加入
            voList.add(ConvertUtils.convertIgnoreBase(permissionVo, PermissionPo.class));
            this.setRolePermission(voList, f.getKey());
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
        List<PermissionPo> pos = ConvertUtils.convertIgnoreBase(permissionPos, PermissionPo.class);
        redisUtils.setHash(ROLE_PERMISSION_KEY, roleCode, pos);
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
        redisUtils.delHash(ROLE_PERMISSION_KEY, roleCode);
    }

    /**
     * 通过 roleCode 获取缓存中的权限数据
     *
     * @param roleCode 角色code
     * @return {@link List< PermissionVo>}
     * @author liuyongtao
     * @since 2021-1-30 8:22
     */
    public List<PermissionVo> getRolePermissionByRoleCode(String roleCode) {
        return redisUtils.getHash(ROLE_PERMISSION_KEY, roleCode, PermissionVo.class);
    }

    /**
     * 设置角色的权限信息
     *
     * @param permissionPos 权限信息
     * @param roleCode      角色code
     * @author liuyongtao
     * @since 2021-1-30 8:26
     */
    public void setRolePermission(List<PermissionPo> permissionPos, String roleCode) {
        redisUtils.setHash(ROLE_PERMISSION_KEY, roleCode, permissionPos);
    }

    /**
     * 查询出角色的信息
     *
     * @return {@link Map< String, List<PermissionVo>>} key-roleCode,value-List<PermissionPo>
     * @author liuyongtao
     * @since 2021-1-30 8:30
     */
    public Map<String, List<PermissionVo>> getRolePermissionMap() {
        return redisUtils.getHashAll(ROLE_PERMISSION_KEY, PermissionVo.class);
    }
}
