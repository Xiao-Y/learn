package com.billow.auth.api;

import com.billow.auth.pojo.po.PermissionPo;
import com.billow.auth.pojo.vo.PermissionVo;
import com.billow.auth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限管理
 *
 * @author liuyongtao
 * @create 2019-07-09 15:31
 */
@Slf4j
@Api("权限管理")
@RestController
@RequestMapping("/permissionApi")
public class PermissionApi {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("根据条件查询权限列表")
    @PostMapping("/findPermissionList")
    public Page<PermissionPo> findPermissionList(@RequestBody PermissionVo permissionVo) throws Exception {
        Page<PermissionPo> page = permissionService.findPermissionList(permissionVo);
        return page;
    }
}
