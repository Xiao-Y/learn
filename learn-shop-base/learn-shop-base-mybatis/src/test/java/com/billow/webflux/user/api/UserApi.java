package com.billow.webflux.user.api;

import com.billow.webflux.user.pojo.build.UserBuildParam;
import com.billow.webflux.user.pojo.vo.UserVo;
import com.billow.webflux.user.pojo.search.UserSearchParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.webflux.user.service.UserService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.webflux.user.pojo.po.UserPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-12-14
 * @version v1.0
 */
@Slf4j
@Api(tags = {"UserApi"},value = "")
@RestController
@RequestMapping("/userApi")
public class UserApi {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findListByPage")
    public IPage<UserPo> findListByPage(@RequestBody UserSearchParam userSearchParam){
        return userService.findListByPage(userSearchParam);
    }

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/findById/{id}")
    public UserVo findById(@PathVariable("id") String id){
        UserPo po = userService.getById(id);
        return ConvertUtils.convert(po, UserVo.class);
    }

    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public UserVo add(@RequestBody UserBuildParam userBuildParam){
        UserPo po = ConvertUtils.convert(userBuildParam, UserPo.class);
        userService.save(po);
        return ConvertUtils.convert(po, UserVo.class);
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return userService.removeById(id);
    }

    @ApiOperation(value = "更新数据")
    @PutMapping(value = "/update")
    public UserVo update(@RequestBody UserBuildParam userBuildParam){
        UserPo po = ConvertUtils.convert(userBuildParam, UserPo.class);
        userService.updateById(po);
        return ConvertUtils.convert(po, UserVo.class);
    }

    @ApiOperation("根据ID禁用数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return userService.prohibitById(id);
    }
}
