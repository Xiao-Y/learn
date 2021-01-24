package com.billow.seckill.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.vo.SeckillVo;
import com.billow.seckill.service.SeckillService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀库存表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Slf4j
@Api(tags = {"SeckillApi"}, value = "秒杀库存表")
@RestController
@RequestMapping("/seckillApi")
public class SeckillApi {

    @Autowired
    private SeckillService seckillService;

    @ApiOperation(value = "查询分页秒杀库存表数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SeckillPo> findListByPage(@RequestBody SeckillVo seckillVo) {
        return seckillService.findListByPage(seckillVo);
    }

    @ApiOperation(value = "根据id查询秒杀库存表数据")
    @GetMapping(value = "/findById/{id}")
    public SeckillVo findById(@PathVariable("id") Long id) {
        SeckillPo po = seckillService.getById(id);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation(value = "新增秒杀库存表数据")
    @PostMapping(value = "/add")
    public SeckillVo add(@RequestBody SeckillVo seckillVo) {
        SeckillPo po = ConvertUtils.convert(seckillVo, SeckillPo.class);
        seckillService.save(po);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation(value = "删除秒杀库存表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") Long id) {
        return seckillService.removeById(id);
    }

    @ApiOperation(value = "更新秒杀库存表数据")
    @PutMapping(value = "/update")
    public SeckillVo update(@RequestBody SeckillVo seckillVo) {
        SeckillPo po = ConvertUtils.convert(seckillVo, SeckillPo.class);
        seckillService.updateById(po);
        return ConvertUtils.convert(po, SeckillVo.class);
    }

    @ApiOperation("根据ID禁用秒杀库存表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable Long id) {
        return seckillService.prohibitById(id);
    }

    @ApiOperation(value = "自动任务加载数据到缓存中")
    @GetMapping(value = "/loadSeckillJob")
    public void loadSeckillJob() {
        seckillService.loadSeckillJob();
    }
}
