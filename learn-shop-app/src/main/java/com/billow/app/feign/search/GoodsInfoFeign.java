package com.billow.app.feign.search;

import com.billow.product.interfaces.api.GoodsSpuApi;
import com.billow.search.interfaces.api.GoodsInfoApi;
import com.billow.search.interfaces.constant.ContextPath;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品搜索相关操作
 *
 * @author liuyongtao
 * @since 2021-9-7 8:39
 */
@FeignClient(contextId = "GoodsInfoApi", name = ContextPath.FEIGN_SERVER_CORE_SEARCH)
public interface GoodsInfoFeign extends GoodsInfoApi {
}
