package com.billow.system.api;

import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Page<PermissionVo> findPermissionList(@RequestBody PermissionVo permissionVo) throws Exception {
        Page<PermissionVo> permissionList = permissionService.findPermissionList(permissionVo);
        return permissionList;
    }

    @ApiOperation("查询权限列表")
    @GetMapping("/findPermissionAll")
    public List<PermissionVo> findPermissionAll() throws Exception {
        List<PermissionVo> permissionList = permissionService.findPermissionAll();
        return permissionList;
    }

    @ApiOperation("根据ID删除权限")
    @DeleteMapping("/deletePermissionById/{id}")
    public PermissionVo deletePermissionById(@PathVariable Long id) {
        PermissionVo permissionVo = permissionService.deletePermissionById(id);
        return permissionVo;
    }

    @ApiOperation("根据ID禁用权限")
    @PutMapping("/prohibitPermissionById/{id}")
    public PermissionVo prohibitPermissionById(@PathVariable Long id) {
        PermissionVo permissionVo = permissionService.prohibitPermissionById(id);
        return permissionVo;
    }

    @ApiOperation("添加权限信息")
    @PostMapping("/savePermission")
    public PermissionVo savePermission(@RequestBody PermissionVo permissionVo) {
        permissionService.savePermission(permissionVo);
        return permissionVo;
    }

    @ApiOperation("更新权限信息")
    @PutMapping("/updatePermission")
    public PermissionVo updatePermission(@RequestBody PermissionVo permissionVo) {
        permissionService.updatePermission(permissionVo);
        return permissionVo;
    }
}
