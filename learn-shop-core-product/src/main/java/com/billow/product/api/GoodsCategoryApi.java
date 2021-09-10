package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.common.ex.SelectEx;
import com.billow.product.pojo.build.GoodsCategoryBuildParam;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.search.GoodsCategorySearchParam;
import com.billow.product.pojo.vo.GoodsCategoryTreeVo;
import com.billow.product.pojo.vo.GoodsCategoryVo;
import com.billow.product.service.GoodsCategoryService;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
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
import java.util.List;
import java.util.stream.Collectors;

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
public class GoodsCategoryApi extends HighLevelApi<GoodsCategoryService, GoodsCategoryPo, GoodsCategoryVo, GoodsCategoryBuildParam, GoodsCategorySearchParam> {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "查询分类下拉列表数据")
    @PostMapping(value = "/findCategorySelect")
    public List<SelectEx> findCategorySelect(@RequestBody GoodsCategoryVo goodsCategoryVo) {
        List<SelectEx> selectExes = new ArrayList<>();
        List<GoodsCategoryPo> list = goodsCategoryService.findList(goodsCategoryVo);
        list.forEach(f -> {
            SelectEx ex = new SelectEx();
            ex.setFieldDisplay(f.getCategoryName());
            ex.setFieldValue(f.getId().toString());
            ex.setFieldOrder(f.getCategorySort());
            selectExes.add(ex);
        });
        selectExes.sort(Comparator.comparing(SelectEx::getFieldOrder, Comparator.nullsFirst(Long::compareTo)));
        return selectExes;
    }

    @ApiOperation(value = "通过父ID查询分类树")
    @GetMapping(value = "/findCategoryTree/{parentId}")
    public List<GoodsCategoryTreeVo> findCategoryTree(@PathVariable Long parentId)  {
        List<GoodsCategoryPo> list = goodsCategoryService.findCategoryTree(parentId);
        return list.stream()
                .map(m -> ConvertUtils.convert(m, GoodsCategoryTreeVo.class))
                .collect(Collectors.toList());
    }
}
