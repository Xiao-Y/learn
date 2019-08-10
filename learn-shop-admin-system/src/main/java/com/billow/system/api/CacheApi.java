package com.billow.system.api;

import com.billow.system.init.StartLoading;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存管理
 *
 * @author liuyongtao
 * @create 2019-08-10 11:06
 */
@Slf4j
@RestController
@RequestMapping("/cacheApi")
@Api(value = "缓存管理")
public class CacheApi {

    @Autowired
    private StartLoading startLoading;

    @PutMapping("/initAll")
    @ApiOperation("初始化所有缓存，initDictionary,initRoleMenu,initRolePermission")
    public boolean initAll() {
        return startLoading.init(null);
    }

    @PutMapping("/init/{cacheType}")
    @ApiOperation("初始化指定的缓存，initDictionary,initRoleMenu,initRolePermission")
    public boolean initCacheByType(@PathVariable("cacheType") String cacheType) {
        return startLoading.init(cacheType);
    }
}
