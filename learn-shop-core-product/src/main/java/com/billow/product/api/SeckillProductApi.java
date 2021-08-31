package com.billow.product.api;

import com.billow.product.api.HighLevelApi;
import com.billow.product.pojo.build.SeckillProductBuildParam;
import com.billow.product.pojo.po.SeckillProductPo;
import com.billow.product.pojo.search.SeckillProductSearchParam;
import com.billow.product.pojo.vo.SeckillProductVo;
import com.billow.product.service.SeckillProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 * @version v2.0
 */
@Slf4j
@Api(tags = {"SeckillProductApi"},value = "限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。")
@RestController
@RequestMapping("/seckillProductApi")
public class SeckillProductApi extends HighLevelApi<SeckillProductService, SeckillProductPo, SeckillProductVo, SeckillProductBuildParam, SeckillProductSearchParam> {

}
