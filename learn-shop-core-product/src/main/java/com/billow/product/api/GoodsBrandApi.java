package com.billow.product.api;

import com.billow.common.ex.SelectEx;
import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsBrandBuildParam;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.search.GoodsBrandSearchParam;
import com.billow.product.pojo.vo.GoodsBrandVo;
import com.billow.product.service.GoodsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Api(tags = {"GoodsBrandApi"}, value = "品牌表")
@RestController
@RequestMapping("/goodsBrandApi")
public class GoodsBrandApi extends HighLevelApi<GoodsBrandService, GoodsBrandPo, GoodsBrandVo, GoodsBrandBuildParam, GoodsBrandSearchParam> {
    @ApiOperation(value = "查询品牌下拉列表数据")
    @PostMapping(value = "/findBrandSelect")
    public List<SelectEx> findBrandSelect(@RequestBody GoodsBrandVo goodsBrandVo) {
        List<SelectEx> selectExes = new ArrayList<>();
        List<GoodsBrandPo> list = this.getService().list();
        list.forEach(f -> {
            SelectEx ex = new SelectEx();
            ex.setFieldDisplay(f.getBrandName());
            ex.setFieldValue(f.getId().toString());
            ex.setFieldOrder(f.getBrandSort());
            selectExes.add(ex);
        });
        selectExes.sort(Comparator.comparing(SelectEx::getFieldOrder, Comparator.nullsFirst(Long::compareTo)));
        return selectExes;
    }
}
