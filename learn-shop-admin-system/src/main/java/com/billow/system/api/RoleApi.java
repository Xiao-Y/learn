package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色信息
 *
 * @author liuyongtao
 * @create 2018-11-05 16:13
 */
@Slf4j
@RestController
@RequestMapping("/roleApi")
@Api(value = "RoleApi", description = "角色信息")
public class RoleApi extends BaseApi {

    @Autowired
    private RoleService roleService;

    @GetMapping("/findRolesInfoByUserId/{userId}")
    public List<RoleVo> findRolesInfoByUserId(@PathVariable("userId") Long userId) {
        List<RoleVo> roleVoList = roleService.findRoleListInfoByUserId(userId);
        return roleVoList;
    }
}
