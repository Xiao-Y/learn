package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.vo.GoodsSpecKeyVo;
import com.billow.product.service.GoodsSpecKeyService;
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

import java.util.List;

/**
 * <p>
 * 规格表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Api(tags = {"GoodsSpecKeyApi"}, value = "规格表")
@RestController
@RequestMapping("/goodsSpecKeyApi")
public class GoodsSpecKeyApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSpecKeyService goodsSpecKeyService;

    @ApiOperation(value = "查询分页规格表数据")
    @PostMapping(value = "/list")
    public IPage<GoodsSpecKeyPo> findListByPage(@RequestBody GoodsSpecKeyVo goodsSpecKeyVo) {
        return goodsSpecKeyService.findListByPage(goodsSpecKeyVo);
    }

    @ApiOperation(value = "根据id查询规格表数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsSpecKeyVo getById(@PathVariable("id") String id) {
        GoodsSpecKeyPo po = goodsSpecKeyService.getById(id);
        return ConvertUtils.convert(po, GoodsSpecKeyVo.class);
    }

    @ApiOperation(value = "新增规格表数据")
    @PostMapping(value = "/add")
    public GoodsSpecKeyVo add(@RequestBody GoodsSpecKeyVo goodsSpecKeyVo) {
        GoodsSpecKeyPo po = ConvertUtils.convert(goodsSpecKeyVo, GoodsSpecKeyPo.class);
        goodsSpecKeyService.save(po);
        return ConvertUtils.convert(po, GoodsSpecKeyVo.class);
    }

    @ApiOperation(value = "删除规格表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id) {
        return goodsSpecKeyService.removeById(id);
    }

    @ApiOperation(value = "更新规格表数据")
    @PutMapping(value = "/update")
    public GoodsSpecKeyVo update(@RequestBody GoodsSpecKeyVo goodsSpecKeyVo) {
        GoodsSpecKeyPo po = ConvertUtils.convert(goodsSpecKeyVo, GoodsSpecKeyPo.class);
        goodsSpecKeyService.updateById(po);
        return ConvertUtils.convert(po, GoodsSpecKeyVo.class);
    }

    @ApiOperation("根据ID禁用规格表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSpecKeyService.prohibitById(id);
    }

    @ApiOperation(value = "通过 CategoryId 查询出所有的规格 KEY")
    @GetMapping(value = "/findListByCategoryId/{categoryId}")
    public List<GoodsSpecKeyPo> findListByCategoryId(@PathVariable("categoryId") String categoryId) {
        return goodsSpecKeyService.findListByCategoryId(categoryId);
    }

    @ApiOperation(value = "保存一组规格信息")
    @PostMapping(value = "/saveList")
    public  List<GoodsSpecKeyVo> saveList(@RequestBody List<GoodsSpecKeyVo> goodsSpecKeyVos) {
        return goodsSpecKeyService.saveList(goodsSpecKeyVos);
    }
}
