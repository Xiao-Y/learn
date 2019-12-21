package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSpuService;
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
 * spu表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Api(tags = {"GoodsSpuApi"}, value = "spu表")
@RestController
@RequestMapping("/goodsSpuApi")
public class GoodsSpuApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSpuService goodsSpuService;

    @ApiOperation(value = "查询分页spu表数据")
    @PostMapping(value = "/list")
    public IPage<GoodsSpuPo> findListByPage(@RequestBody GoodsSpuVo goodsSpuVo) {
        return goodsSpuService.findListByPage(goodsSpuVo);
    }

    @ApiOperation(value = "根据id查询spu表数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsSpuVo getById(@PathVariable("id") String id) {
        GoodsSpuPo po = goodsSpuService.getById(id);
        return ConvertUtils.convert(po, GoodsSpuVo.class);
    }

    @ApiOperation(value = "新增spu表数据")
    @PostMapping(value = "/add")
    public GoodsSpuVo add(@RequestBody GoodsSpuVo goodsSpuVo) {
        goodsSpuService.addOrUpdate(goodsSpuVo);
        return goodsSpuVo;
    }

    @ApiOperation(value = "删除spu表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id) {
        return goodsSpuService.removeById(id);
    }

    @ApiOperation(value = "更新spu表数据")
    @PutMapping(value = "/update")
    public GoodsSpuVo update(@RequestBody GoodsSpuVo goodsSpuVo) {
        goodsSpuService.addOrUpdate(goodsSpuVo);
        return goodsSpuVo;
    }

    @ApiOperation("根据ID禁用spu表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSpuService.prohibitById(id);
    }
}
