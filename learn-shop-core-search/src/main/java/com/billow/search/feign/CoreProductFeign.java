package com.billow.search.feign;

import com.billow.product.interfaces.api.CoreProductApi;
import com.billow.product.interfaces.constant.ContextPath;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * product 远程调用
 *
 * @author billow
 * @date 2019/9/1 13:16
 */
@FeignClient(name = ContextPath.FEIGN_SERVER_CORE_PROCUCT)
public interface CoreProductFeign extends CoreProductApi {
}
