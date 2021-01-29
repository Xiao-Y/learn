package com.billow.system.service.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.billow.common.redis.RedisUtils;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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

    public final static String ROLE_PERMISSION_KEY = RedisCst.ROLE_PERMISSION_KEY;

    @Autowired
    private RedisUtils redisUtils;
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;

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
        // 先取出旧值，再删除
        String json = redisUtils.getHash(ROLE_PERMISSION_KEY, oldRoleCode);
        List<PermissionVo> permissionVos = JSON.parseObject(json, new TypeReference<List<PermissionVo>>() {
        });
        this.deleteRoleByRoleCode(oldRoleCode);
        List<PermissionPo> pos = ConvertUtils.convertIgnoreBase(permissionVos, PermissionPo.class);
        redisUtils.setHash(ROLE_PERMISSION_KEY, newRoleCode, JSON.toJSONString(pos));
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
     * 删除 redis 所有角色所持有的该权限
     *
     * @return void
     * @author LiuYongTao
     * @date 2019/7/16 15:02
     */
    public void deleteRolePermissionById(Long id) {
        Set<String> roleKeys = redisTemplate.keys(ROLE_PERMISSION_KEY + "*");
        roleKeys.stream().forEach(f -> {
            List<PermissionVo> permissionVos = redisUtils.getList(f);
            List<PermissionVo> voList = permissionVos.stream()
                    .filter(fi -> !fi.getId().equals(id))
                    .map(m -> ConvertUtils.convertIgnoreBase(m, PermissionVo.class))
                    .collect(Collectors.toList());
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
        // 取出缓存中数据
        Map<String, String> hashAll = redisUtils.getHashAll(ROLE_PERMISSION_KEY);
        hashAll.entrySet().stream().forEach(f -> {
            List<PermissionVo> permissionVos = JSON.parseObject(f.getValue(), new TypeReference<List<PermissionVo>>() {
            });
            // 移除旧权限信息
            List<PermissionPo> voList = permissionVos.stream()
                    .filter(fi -> !fi.getId().equals(permissionVo.getId()))
                    .map(m -> ConvertUtils.convertIgnoreBase(m, PermissionPo.class))
                    .collect(Collectors.toList());
            // 把新的权限信息加入
            voList.add(ConvertUtils.convertIgnoreBase(permissionVo, PermissionPo.class));
            redisUtils.setHash(ROLE_PERMISSION_KEY, f.getKey(), JSON.toJSONString(voList));
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
        redisUtils.setObj(ROLE_PERMISSION_KEY + roleCode, pos);
    }
}
