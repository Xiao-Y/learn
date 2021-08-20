package com.billow.search.feign;

import com.billow.product.interfaces.api.GoodsBrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * product 远程调用
 *
 * @author billow
 * @date 2019/9/1 13:16
 */
@FeignClient(name = "learn-shop-core-product")
public interface GoodsBrandFeign extends GoodsBrandApi {
}
