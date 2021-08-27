package com.billow.seckill.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.build.SeckillSessionBuildParam;
import com.billow.seckill.pojo.po.SeckillSessionPo;
import com.billow.seckill.pojo.search.SeckillSessionSearchParam;
import com.billow.seckill.pojo.vo.SeckillSessionVo;
import com.billow.seckill.service.SeckillSessionService;
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
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 * @version v1.0
 */
@Slf4j
@Api(tags = {"SeckillSessionApi"},value = "限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。")
@RestController
@RequestMapping("/seckillSessionApi")
public class SeckillSessionApi {

    @Autowired
    private SeckillSessionService seckillSessionService;

    @ApiOperation(value = "查询分页限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SeckillSessionPo> findListByPage(@RequestBody SeckillSessionSearchParam seckillSessionSearchParam){
        return seckillSessionService.findListByPage(seckillSessionSearchParam);
    }

    @ApiOperation(value = "根据id查询限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @GetMapping(value = "/findById/{id}")
    public SeckillSessionVo findById(@PathVariable("id") String id){
        SeckillSessionPo po = seckillSessionService.getById(id);
        return ConvertUtils.convert(po, SeckillSessionVo.class);
    }

    @ApiOperation(value = "新增限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @PostMapping(value = "/add")
    public SeckillSessionVo add(@RequestBody SeckillSessionBuildParam seckillSessionBuildParam){
        SeckillSessionPo po = ConvertUtils.convert(seckillSessionBuildParam, SeckillSessionPo.class);
        seckillSessionService.save(po);
        return ConvertUtils.convert(po, SeckillSessionVo.class);
    }

    @ApiOperation(value = "删除限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return seckillSessionService.removeById(id);
    }

    @ApiOperation(value = "更新限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @PutMapping(value = "/update")
    public SeckillSessionVo update(@RequestBody SeckillSessionBuildParam seckillSessionBuildParam){
        SeckillSessionPo po = ConvertUtils.convert(seckillSessionBuildParam, SeckillSessionPo.class);
        seckillSessionService.updateById(po);
        return ConvertUtils.convert(po, SeckillSessionVo.class);
    }

    @ApiOperation("根据ID禁用限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return seckillSessionService.prohibitById(id);
    }
}
