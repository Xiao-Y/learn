package com.billow.auth.init;

import com.billow.auth.dao.PermissionDao;
import com.billow.auth.dao.RoleDao;
import com.billow.auth.dao.RolePermissionDao;
import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePermissionPo;
import com.billow.auth.pojo.po.RolePo;
import com.billow.auth.service.PermissionService;
import com.billow.auth.utils.RedisUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 初始化角色权限,保存到 redis 中
 *
 * @author liuyongtao
 * @create 2019-05-23 19:34
 */
@Component
public class initRolePermission implements InitializingBean {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<RolePo> rolePos = roleDao.findAll();
        for (RolePo rolePo : rolePos) {
            Set<PermissionPo> permissionPos = permissionService.findPermissionByRole(rolePo);
            redisUtils.setObj("ROLE:" + rolePo.getRoleCode(), permissionPos);
        }
    }
}
