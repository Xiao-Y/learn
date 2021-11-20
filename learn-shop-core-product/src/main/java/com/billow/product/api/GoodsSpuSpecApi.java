package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSpuSpecBuildParam;
import com.billow.product.pojo.search.GoodsSpuSpecSearchParam;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.vo.GoodsSpuSpecVo;
import com.billow.product.service.GoodsSpuSpecService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * spu规格表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsSpuSpecApi"},value = "spu规格表")
@RestController
@RequestMapping("/goodsSpuSpecApi")
public class GoodsSpuSpecApi extends HighLevelApi<GoodsSpuSpecService, GoodsSpuSpecPo, GoodsSpuSpecVo, GoodsSpuSpecBuildParam, GoodsSpuSpecSearchParam> {

}
