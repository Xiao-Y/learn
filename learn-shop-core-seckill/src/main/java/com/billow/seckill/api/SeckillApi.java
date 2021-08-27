package com.billow.seckill.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.build.SeckillBuildParam;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.search.SeckillSearchParam;
import com.billow.seckill.pojo.vo.SeckillVo;
import com.billow.seckill.service.SeckillService;
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
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 * @version v1.0
 */
@Slf4j
@Api(tags = {"SeckillApi"},value = "限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。")
@RestController
@RequestMapping("/seckillApi")
public class SeckillApi {

    @Autowired
    private SeckillService seckillService;

    @ApiOperation(value = "查询分页限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SeckillPo> findListByPage(@RequestBody SeckillSearchParam seckillSearchParam){
        return seckillService.findListByPage(seckillSearchParam);
    }

    @ApiOperation(value = "根据id查询限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @GetMapping(value = "/findById/{id}")
    public SeckillVo findById(@PathVariable("id") String id){
        SeckillPo po = seckillService.getById(id);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation(value = "新增限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @PostMapping(value = "/add")
    public SeckillVo add(@RequestBody SeckillBuildParam seckillBuildParam){
        SeckillPo po = ConvertUtils.convert(seckillBuildParam, SeckillPo.class);
        seckillService.save(po);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation(value = "删除限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return seckillService.removeById(id);
    }

    @ApiOperation(value = "更新限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @PutMapping(value = "/update")
    public SeckillVo update(@RequestBody SeckillBuildParam seckillBuildParam){
        SeckillPo po = ConvertUtils.convert(seckillBuildParam, SeckillPo.class);
        seckillService.updateById(po);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation("根据ID禁用限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return seckillService.prohibitById(id);
    }
}
