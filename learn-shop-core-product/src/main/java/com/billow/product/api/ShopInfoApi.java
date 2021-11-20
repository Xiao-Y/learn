package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.ShopInfoBuildParam;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.search.ShopInfoSearchParam;
import com.billow.product.pojo.vo.ShopInfoVo;
import com.billow.product.service.ShopInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"ShopInfoApi"},value = "店铺表")
@RestController
@RequestMapping("/shopInfoApi")
public class ShopInfoApi extends HighLevelApi<ShopInfoService, ShopInfoPo, ShopInfoVo, ShopInfoBuildParam, ShopInfoSearchParam> {

}
