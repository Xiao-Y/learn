package com.billow.seckill.api;

import com.billow.seckill.pojo.vo.SuccessKilledVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.seckill.service.SuccessKilledService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀成功明细表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-01-21
 * @version v1.0
 */
@Slf4j
@Api(tags = {"SuccessKilledApi"},value = "秒杀成功明细表")
@RestController
@RequestMapping("/successKilledApi")
public class SuccessKilledApi {

    @Autowired
    private SuccessKilledService successKilledService;

    @ApiOperation(value = "查询分页秒杀成功明细表数据")
    @PostMapping(value = "/findListByPage")
    public IPage<SuccessKilledPo> findListByPage(@RequestBody SuccessKilledVo successKilledVo){
        return successKilledService.findListByPage(successKilledVo);
    }

    @ApiOperation(value = "根据id查询秒杀成功明细表数据")
    @GetMapping(value = "/findById/{id}")
    public SuccessKilledVo findById(@PathVariable("id") String id){
        SuccessKilledPo po = successKilledService.getById(id);
        return ConvertUtils.convert(po, SuccessKilledVo.class);
    }

    @ApiOperation(value = "新增秒杀成功明细表数据")
    @PostMapping(value = "/add")
    public SuccessKilledVo add(@RequestBody SuccessKilledVo successKilledVo){
        SuccessKilledPo po = ConvertUtils.convert(successKilledVo, SuccessKilledPo.class);
        successKilledService.save(po);
        return ConvertUtils.convert(po, SuccessKilledVo.class);
    }

    @ApiOperation(value = "删除秒杀成功明细表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return successKilledService.removeById(id);
    }

    @ApiOperation(value = "更新秒杀成功明细表数据")
    @PutMapping(value = "/update")
    public SuccessKilledVo update(@RequestBody SuccessKilledVo successKilledVo){
        SuccessKilledPo po = ConvertUtils.convert(successKilledVo, SuccessKilledPo.class);
        successKilledService.updateById(po);
        return ConvertUtils.convert(po, SuccessKilledVo.class);
    }

    @ApiOperation("根据ID禁用秒杀成功明细表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable Long id) {
        return successKilledService.prohibitById(id);
    }
}
