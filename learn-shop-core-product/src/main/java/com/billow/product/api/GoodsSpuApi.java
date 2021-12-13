package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSpuBuildParam;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSpuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Api(tags = {"GoodsSpuApi"}, value = "spu")
@RestController
@RequestMapping("/goodsSpuApi")
public class GoodsSpuApi extends HighLevelApi<GoodsSpuService, GoodsSpuPo, GoodsSpuVo, GoodsSpuBuildParam, GoodsSpuSearchParam> {

}
