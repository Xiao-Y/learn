package com.billow.app.feign.product;

import com.billow.product.interfaces.api.GoodsSpuApi;
import com.billow.product.interfaces.constant.ContextPath;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author liuyongtao
 * @since 2021-9-7 8:39
 */
@FeignClient(contextId = "GoodsSpuApi", name = ContextPath.FEIGN_SERVER_CORE_PROCUCT)
public interface GoodsSpuFeign extends GoodsSpuApi {
}
