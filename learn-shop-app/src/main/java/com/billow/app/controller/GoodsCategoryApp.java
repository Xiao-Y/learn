package com.billow.app.controller;

import com.billow.app.feign.product.GoodsCategoryFeign;
import com.billow.product.interfaces.vo.GoodsCategoryTreeVo;
import com.billow.product.interfaces.vo.GoodsCategoryVo;
import com.billow.tools.resData.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/goodsCategoryApp")
public class GoodsCategoryApp {

    @Autowired
    private GoodsCategoryFeign goodsCategoryFeign;

    /**
     * 根据id查询分类数据
     *
     * @param id
     * @return {@link GoodsCategoryVo}
     * @author xiaoy
     * @since 2021/2/4 16:20
     */
    @GetMapping(value = "/getById/{id}")
    BaseResponse<GoodsCategoryVo> getCategoryById(@PathVariable("id") Long id) {
        return goodsCategoryFeign.getCategoryById(id);
    }

    @ApiOperation(value = "通过父ID查询分类树")
    @GetMapping(value = "/findCategoryTree/{parentId}")
    public BaseResponse<List<GoodsCategoryTreeVo>> findCategoryTree(@PathVariable Long parentId) {
        return goodsCategoryFeign.findCategoryTree(parentId);
    }
}
