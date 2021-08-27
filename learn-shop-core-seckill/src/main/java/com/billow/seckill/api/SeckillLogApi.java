package com.billow.seckill.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.build.SeckillLogBuildParam;
import com.billow.seckill.pojo.po.SeckillLogPo;
import com.billow.seckill.pojo.search.SeckillLogSearchParam;
import com.billow.seckill.pojo.vo.SeckillLogVo;
import com.billow.seckill.service.SeckillLogService;
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
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 * @version v1.0
 */
@Slf4j
@Api(tags = {"SeckillLogApi"},value = "限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。")
@RestController
@RequestMapping("/seckillLogApi")
public class SeckillLogApi {

    @Autowired
    private SeckillLogService seckillLogService;

    @ApiOperation(value = "查询分页限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SeckillLogPo> findListByPage(@RequestBody SeckillLogSearchParam seckillLogSearchParam){
        return seckillLogService.findListByPage(seckillLogSearchParam);
    }

    @ApiOperation(value = "根据id查询限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @GetMapping(value = "/findById/{id}")
    public SeckillLogVo findById(@PathVariable("id") String id){
        SeckillLogPo po = seckillLogService.getById(id);
        return ConvertUtils.convert(po, SeckillLogVo.class);
    }

    @ApiOperation(value = "新增限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @PostMapping(value = "/add")
    public SeckillLogVo add(@RequestBody SeckillLogBuildParam seckillLogBuildParam){
        SeckillLogPo po = ConvertUtils.convert(seckillLogBuildParam, SeckillLogPo.class);
        seckillLogService.save(po);
        return ConvertUtils.convert(po, SeckillLogVo.class);
    }

    @ApiOperation(value = "删除限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return seckillLogService.removeById(id);
    }

    @ApiOperation(value = "更新限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @PutMapping(value = "/update")
    public SeckillLogVo update(@RequestBody SeckillLogBuildParam seckillLogBuildParam){
        SeckillLogPo po = ConvertUtils.convert(seckillLogBuildParam, SeckillLogPo.class);
        seckillLogService.updateById(po);
        return ConvertUtils.convert(po, SeckillLogVo.class);
    }

    @ApiOperation("根据ID禁用限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return seckillLogService.prohibitById(id);
    }
}
