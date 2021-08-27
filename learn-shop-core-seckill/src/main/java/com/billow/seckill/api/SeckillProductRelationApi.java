package com.billow.seckill.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.build.SeckillProductRelationBuildParam;
import com.billow.seckill.pojo.po.SeckillProductRelationPo;
import com.billow.seckill.pojo.search.SeckillProductRelationSearchParam;
import com.billow.seckill.pojo.vo.SeckillProductRelationVo;
import com.billow.seckill.service.SeckillProductRelationService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 * @version v1.0
 */
@Slf4j
@Api(tags = {"SeckillProductRelationApi"},value = "限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。")
@RestController
@RequestMapping("/seckillProductRelationApi")
public class SeckillProductRelationApi {

    @Autowired
    private SeckillProductRelationService seckillProductRelationService;

    @ApiOperation(value = "查询分页限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SeckillProductRelationPo> findListByPage(@RequestBody SeckillProductRelationSearchParam seckillProductRelationSearchParam){
        return seckillProductRelationService.findListByPage(seckillProductRelationSearchParam);
    }

    @ApiOperation(value = "根据id查询限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @GetMapping(value = "/findById/{id}")
    public SeckillProductRelationVo findById(@PathVariable("id") String id){
        SeckillProductRelationPo po = seckillProductRelationService.getById(id);
        return ConvertUtils.convert(po, SeckillProductRelationVo.class);
    }

    @ApiOperation(value = "新增限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @PostMapping(value = "/add")
    public SeckillProductRelationVo add(@RequestBody SeckillProductRelationBuildParam seckillProductRelationBuildParam){
        SeckillProductRelationPo po = ConvertUtils.convert(seckillProductRelationBuildParam, SeckillProductRelationPo.class);
        seckillProductRelationService.save(po);
        return ConvertUtils.convert(po, SeckillProductRelationVo.class);
    }

    @ApiOperation(value = "删除限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return seckillProductRelationService.removeById(id);
    }

    @ApiOperation(value = "更新限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @PutMapping(value = "/update")
    public SeckillProductRelationVo update(@RequestBody SeckillProductRelationBuildParam seckillProductRelationBuildParam){
        SeckillProductRelationPo po = ConvertUtils.convert(seckillProductRelationBuildParam, SeckillProductRelationPo.class);
        seckillProductRelationService.updateById(po);
        return ConvertUtils.convert(po, SeckillProductRelationVo.class);
    }

    @ApiOperation("根据ID禁用限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return seckillProductRelationService.prohibitById(id);
    }
}
