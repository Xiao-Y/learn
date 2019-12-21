package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.common.business.ex.SelectEx;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.vo.GoodsCategoryVo;
import com.billow.product.service.GoodsCategoryService;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Api(tags = {"GoodsCategoryApi"}, value = "分类表")
@RestController
@RequestMapping("/goodsCategoryApi")
public class GoodsCategoryApi {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "查询分页分类表数据")
    @PostMapping(value = "/list")
    public IPage<GoodsCategoryPo> findListByPage(@RequestBody GoodsCategoryVo goodsCategoryVo) {
        return goodsCategoryService.findListByPage(goodsCategoryVo);
    }

    @ApiOperation(value = "根据id查询分类表数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsCategoryVo getById(@PathVariable("id") String id) {
        GoodsCategoryPo po = goodsCategoryService.getById(id);
        return ConvertUtils.convert(po, GoodsCategoryVo.class);
    }

    @ApiOperation(value = "新增分类表数据")
    @PostMapping(value = "/add")
    public GoodsCategoryVo add(@RequestBody GoodsCategoryVo goodsCategoryVo) {
        GoodsCategoryPo po = ConvertUtils.convert(goodsCategoryVo, GoodsCategoryPo.class);
        goodsCategoryService.save(po);
        return ConvertUtils.convert(po, GoodsCategoryVo.class);
    }

    @ApiOperation(value = "删除分类表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id) {
        return goodsCategoryService.removeById(id);
    }

    @ApiOperation(value = "更新分类表数据")
    @PutMapping(value = "/update")
    public GoodsCategoryVo update(@RequestBody GoodsCategoryVo goodsCategoryVo) {
        GoodsCategoryPo po = ConvertUtils.convert(goodsCategoryVo, GoodsCategoryPo.class);
        goodsCategoryService.updateById(po);
        return ConvertUtils.convert(po, GoodsCategoryVo.class);
    }

    @ApiOperation("根据ID禁用分类表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsCategoryService.prohibitById(id);
    }

    @ApiOperation(value = "查询分类下拉列表数据")
    @PostMapping(value = "/findCategorySelect")
    public List<SelectEx> findCategorySelect(@RequestBody GoodsCategoryVo goodsCategoryVo) {
        List<SelectEx> selectExes = new ArrayList<>();
        List<GoodsCategoryPo> list = goodsCategoryService.findList(goodsCategoryVo);
        list.forEach(f -> {
            SelectEx ex = new SelectEx();
            ex.setFieldDisplay(f.getCategoryName());
            ex.setFieldValue(f.getId());
            ex.setFieldOrder(f.getCategorySort());
            selectExes.add(ex);
        });
        selectExes.sort(Comparator.comparing(SelectEx::getFieldOrder, Comparator.nullsFirst(Long::compareTo)));
        return selectExes;
    }
}
