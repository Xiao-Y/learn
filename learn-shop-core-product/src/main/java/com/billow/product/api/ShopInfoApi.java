package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.build.ShopInfoBuildParam;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.search.ShopInfoSearchParam;
import com.billow.product.service.ShopInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺表 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "ShopInfoApi", description = "店铺表")
@RestController
@RequestMapping("/shopInfoApi")
public class ShopInfoApi extends HighLevelV2Api<ShopInfoService, ShopInfoPo, ShopInfoSearchParam> {

}
