package com.billow.auth.service.impl;

import com.billow.auth.dao.PermissionDao;
import com.billow.auth.dao.RolePermissionDao;
import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.po.RolePermissionPo;
import com.billow.auth.pojo.po.RolePo;
import com.billow.auth.pojo.vo.PermissionVo;
import com.billow.auth.service.PermissionService;
import com.billow.auth.utils.ConvertUtils;
import com.billow.auth.utils.DefaultSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
public class CustomPermissionServiceImpl implements PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

//    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

//    @Override
//    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

//        boolean isPermission = false;
//
//        Object principal = authentication.getPrincipal();
//        logger.info("===>>> principal:{}", principal);
//        if ("admin".equals(principal)) {
//            return true;
//        }
//
//        String contextPath = request.getContextPath();
//        logger.info("request.getContextPath:{}", contextPath);
//        String targetURI = request.getRequestURI();
//        logger.info("<<<=== targetURI:{}", targetURI);
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        if (authorities != null && authorities.size() > 0) {
//            for (GrantedAuthority authority : authorities) {
//                String sourceURI = contextPath + authority.getAuthority();
//                logger.info("===>>> sourceURI:{}", sourceURI);
//                if (antPathMatcher.match(sourceURI, targetURI)) {
//                    isPermission = true;
//                    break;
//                }
//            }
//        }
//        return isPermission;
//    }

    @Override
    public Set<PermissionPo> findPermissionByRole(RolePo rolePo) {

        Set<PermissionPo> permissionPos = new HashSet<>();

        // 查询权限信息
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(rolePo.getId());
        if (CollectionUtils.isEmpty(rolePermissionPos)) {
            logger.warn("角色：{}，未分配权限！", rolePo.getRoleName());
            return permissionPos;
        }

        rolePermissionPos.stream().forEach(rp -> {
            Optional<PermissionPo> permissionPo = permissionDao.findByIdAndValidIndIsTrue(rp.getPermissionId());
            if (!permissionPo.isPresent()) {
                logger.warn("角色：{}，permissionId:{},未查询到信息！", rolePo.getRoleName(), rp.getId());
                return;
            }
            permissionPos.add(permissionPo.get());
        });

        return permissionPos;
    }

    @Override
    public Page<PermissionPo> findPermissionList(PermissionVo permissionVo) {
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        DefaultSpec<PermissionPo> defaultSpec = new DefaultSpec<>(convert);
        Pageable pageable = new PageRequest(permissionVo.getPageNo(), permissionVo.getPageSize());
        Page<PermissionPo> permissionPos = permissionDao.findAll(defaultSpec, pageable);
        return permissionPos;
    }
}
