package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.system.pojo.ex.DataDictionaryEx;
import com.billow.system.service.query.SelectRoleQuery;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.RoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(value = "角色信息")
public class RoleApi extends BaseApi {

    @Autowired
    private RoleService roleService;

    @ApiOperation("根据条件查询角色列表信息")
    @PostMapping("/findRoleList")
    public Page<RolePo> findRoleList(@RequestBody RoleVo roleVo) throws Exception {
        Page<RolePo> page = roleService.findRoleByCondition(roleVo);
        return page;
    }

//    @ApiOperation("根据角色ID查询角色信息")
//    @GetMapping("/findRolesInfoByUserId/{userId}")
//    public List<RoleVo> findRolesInfoByUserId(@PathVariable("userId") Long userId) {
//        List<RoleVo> roleVoList = roleService.findRoleListInfoByUserId(userId);
//        return roleVoList;
//    }

    @ApiOperation("根据角色ID查询权限ID")
    @GetMapping("/findPermissionByRoleId/{roleId}")
    public List<Long> findPermissionByRoleId(@PathVariable("roleId") Long roleId) throws Exception {
        return roleService.findPermissionByRoleId(roleId);
    }

    @ApiOperation("根据角色ID查询菜单ID")
    @GetMapping("/findMenuByRoleId/{roleId}")
    public List<String> findMenuByRoleId(@PathVariable("roleId") Long roleId) throws Exception {
        return roleService.findMenuByRoleId(roleId);
    }

    @ApiOperation("保存角色信息、角色菜单和角色权限")
    @PostMapping("/saveRole")
    public RoleVo saveRole(@RequestBody RoleVo roleVo) throws Exception {
        roleService.saveRole(roleVo);
        return roleVo;
    }

    @ApiOperation("根据id禁用角色信息")
    @PutMapping("/prohibitRoleById/{roleId}")
    public RoleVo prohibitRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.prohibitRoleById(roleId);
    }

    @ApiOperation("根据id删除角色信息")
    @DeleteMapping("/deleteRoleById/{roleId}")
    public RoleVo deleteRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.deleteRoleById(roleId);
    }

    @ApiOperation("加载下拉列表角色信息")
    @GetMapping("/findSelectRole")
    public List<DataDictionaryEx> findSelectRole() {
        return roleService.findSelectRole();
    }

    @ApiOperation("根据id查询角色信息")
    @GetMapping("/findRoleById")
    public List<RoleVo> findRoleById(@RequestParam("ids") List<Long> ids) {
        return roleService.findRoleById(ids);
    }

    @ApiOperation(value = "查询 roleCode 的个数")
    @GetMapping("/checkRoleCode/{roleCode}")
    public Integer checkRoleCode(@PathVariable("roleCode") String roleCode) {
        return roleService.countRoleCodeByRoleCode(roleCode);
    }
}
