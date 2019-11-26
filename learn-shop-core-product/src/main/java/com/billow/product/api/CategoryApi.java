package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.CategoryPo;
import com.billow.product.pojo.vo.CategoryVo;
import com.billow.product.service.CategoryService;
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
 * 商品分类 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 * @version v1.0
 */
@Api(tags = {"商品分类"},value = "商品分类")
@RestController
@RequestMapping("/categoryApi")
public class CategoryApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "查询分页商品分类数据")
    @RequestMapping(value = "/list")
    public IPage<CategoryPo> findListByPage(@RequestBody CategoryVo categoryVo){
        return categoryService.findListByPage(categoryVo);
    }

    @ApiOperation(value = "根据id查询商品分类数据")
    @GetMapping(value = "/getById/{id}")
    public CategoryVo getById(@PathVariable("id") String id){
        CategoryPo po = categoryService.getById(id);
        return ConvertUtils.convert(po, CategoryVo.class);
    }

    @ApiOperation(value = "新增商品分类数据")
    @PostMapping(value = "/add")
    public CategoryVo add(@RequestBody CategoryVo categoryVo){
        CategoryPo po = ConvertUtils.convert(categoryVo, CategoryPo.class);
        categoryService.save(po);
        return ConvertUtils.convert(po, CategoryVo.class);
    }

    @ApiOperation(value = "删除商品分类数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return categoryService.removeById(id);
    }

    @ApiOperation(value = "更新商品分类数据")
    @PutMapping(value = "/update")
    public CategoryVo update(@RequestBody CategoryVo categoryVo){
        CategoryPo po = ConvertUtils.convert(categoryVo, CategoryPo.class);
        categoryService.updateById(po);
        return ConvertUtils.convert(po, CategoryVo.class);
    }

    @ApiOperation("根据ID禁用商品分类数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return categoryService.prohibitById(id);
    }
}
