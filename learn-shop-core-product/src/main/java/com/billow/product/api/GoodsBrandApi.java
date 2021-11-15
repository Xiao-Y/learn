package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsBrandBuildParam;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.search.GoodsBrandSearchParam;
import com.billow.product.pojo.vo.GoodsBrandVo;
import com.billow.product.service.GoodsBrandService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsBrandApi"},value = "品牌表")
@RestController
@RequestMapping("/goodsBrandApi")
public class GoodsBrandApi extends HighLevelApi<GoodsBrandService, GoodsBrandPo, GoodsBrandVo, GoodsBrandBuildParam, GoodsBrandSearchParam>
{

}
