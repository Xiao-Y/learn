package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.vo.ShopInfoVo;
import com.billow.product.service.ShopInfoService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-27
 * @version v1.0
 */
@Api(tags = {"ShopInfoApi"},value = "店铺表")
@RestController
@RequestMapping("/shopInfoApi")
public class ShopInfoApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShopInfoService shopInfoService;

    @ApiOperation(value = "查询分页店铺表数据")
    @PostMapping(value = "/list")
    public IPage<ShopInfoPo> findListByPage(@RequestBody ShopInfoVo shopInfoVo){
        return shopInfoService.findListByPage(shopInfoVo);
    }

    @ApiOperation(value = "根据id查询店铺表数据")
    @GetMapping(value = "/getById/{id}")
    public ShopInfoVo getById(@PathVariable("id") String id){
        ShopInfoPo po = shopInfoService.getById(id);
        return ConvertUtils.convert(po, ShopInfoVo.class);
    }

    @ApiOperation(value = "新增店铺表数据")
    @PostMapping(value = "/add")
    public ShopInfoVo add(@RequestBody ShopInfoVo shopInfoVo){
        ShopInfoPo po = ConvertUtils.convert(shopInfoVo, ShopInfoPo.class);
        shopInfoService.save(po);
        return ConvertUtils.convert(po, ShopInfoVo.class);
    }

    @ApiOperation(value = "删除店铺表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return shopInfoService.removeById(id);
    }

    @ApiOperation(value = "更新店铺表数据")
    @PutMapping(value = "/update")
    public ShopInfoVo update(@RequestBody ShopInfoVo shopInfoVo){
        ShopInfoPo po = ConvertUtils.convert(shopInfoVo, ShopInfoPo.class);
        shopInfoService.updateById(po);
        return ConvertUtils.convert(po, ShopInfoVo.class);
    }

    @ApiOperation("根据ID禁用店铺表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return shopInfoService.prohibitById(id);
    }
}
